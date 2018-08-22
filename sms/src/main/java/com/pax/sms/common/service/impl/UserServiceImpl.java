package com.pax.sms.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.pax.sms.common.dao.UserMapper;
import com.pax.sms.common.domain.User;
import com.pax.sms.common.model.AddUserInput;
import com.pax.sms.common.model.UserLoginInfo;
import com.pax.sms.common.service.UserService;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.exception.BusinessException;
import com.pax.sms.core.exception.SystemException;
import com.pax.sms.core.exception.TimeoutException;
import com.pax.sms.core.utils.DESUtil;
import com.pax.sms.core.utils.EmailHelper;
import com.pax.sms.core.utils.JwtTokenUtils;
import com.pax.sms.core.utils.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.pax.sms.common.service.impl
 *
 * @author yyyty
 * @time :  2018/6/22
 * @description:
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private ConfigConstant configConstant;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${active_user_account_email_url}")
    private String activeAccountUrl;

  //  @Transactional
    @Override
    public JSONObject login(String accountName, String password, String verifyCode, String verifyCodeKey) {
        String storeVerifyCode = paxRedisTemplate.get(ConfigConstant.CAPTCHA_TAG, verifyCodeKey);
        //验证码为空，或者验证码不相同
        if (StringUtils.isBlank(storeVerifyCode)) {
            throw new BusinessException(ConfigConstant.INVALID_VERIFY_CODE, "验证码已经过期,请刷新");
        }else if(!verifyCode.equalsIgnoreCase(storeVerifyCode)){
            throw new BusinessException(ConfigConstant.INVALID_VERIFY_CODE, "验证码不正确");
        }
        User user = userMapper.findUserByAccountName(accountName);

        if (null == user) {
            throw new SystemException(ConfigConstant.INVALID_ACCOUNT_OR_PASSWORD, "登录名或密码不正确");
        }
        if(!"1".equals(user.getIsActive())){
            throw new BusinessException("USER-NOT-ACTIVE", "当前用户还未激活");
        }
        if (ConfigConstant.IS_LOCKED.equals(user.getIsLocked())) {
            throw new SystemException(ConfigConstant.USER_BEEN_LOCKED,"该用户已被锁定,请找回密码");
        }
        JSONObject jsonObject = new JSONObject();
        String md5Psw = null;
        try {
            md5Psw = DESUtil.decryption(password, verifyCodeKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }
        String pswSalt = DigestUtils.md5Hex(md5Psw + user.getSalt());
        //密码不正确情况
        if (!pswSalt.equals(user.getPassword())) {
            Integer errorTimes =user.getPswErrorTimes() ;
            user.setPswErrorTimes(errorTimes+1);
            String msg = null;
            if (errorTimes+1 == 5) {
                user.setIsLocked(ConfigConstant.IS_LOCKED);
                msg = "密码连续输错5次,账户已被锁定,请点击找回密码";
            } else {
                msg = "密码不正确，连续错误5次账户将被锁定,错误次数" + (errorTimes+1);
            }
            updateErrTimes(user);
            throw  new BusinessException(msg);
        }
        user.setPswErrorTimes(0);
        user.setLastLoginTime(new Date());
        this.updateNotNull(user);
        String userToekn = null;
        try {
            userToekn = JwtTokenUtils.createToken(user.getUserId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        BeanUtils.copyProperties(user, userLoginInfo);
        userLoginInfo.setToken(userToekn);
        //把用户信息存到redis
        paxRedisTemplate.setWithExpireTimeObj(ConfigConstant.USER_TAG, String.valueOf(user.getUserId()), userLoginInfo, configConstant.getExpire_seconds());
        jsonObject.put(ConfigConstant.ACCESS_TOKEN, userToekn);
        jsonObject.put("role", user.getRole());

        return jsonObject;

    }

    @Transactional
    @Override
    public void register(AddUserInput input) {
        String md5Psw = null;
        try {
            md5Psw = DESUtil.decryption(input.getPassword(), input.getVerifyCode());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }

        String email = input.getEmail();
        User emailStored = userMapper.findUserByAccountName(email);
        //邮箱存在，且已激活
        if (emailStored != null) {
            if("1".equals(emailStored.getIsActive())){
                throw new BusinessException("该邮箱已被注册!");
            }
            else{
             //   userMapper.delete(emailStored);
                userMapper.deleteByPrimaryKey(emailStored.getUserId());
            }
        }
        //生成盐值
        String salt = captchaProducer.createText();
        //与盐值混淆加密
        String pswSalt = DigestUtils.md5Hex(md5Psw + salt);

        User user = new User();
        BeanUtils.copyProperties(input, user);
        user.setSalt(salt);
        user.setPassword(pswSalt);
        user.setIsSendEmail("1");
        int num = mapper.insertSelective(user);
        logger.info("新增用户信息[{}]:", user.toString());

     /*   //生成一个随机码，存到缓存，作为用户激活的key，30分钟过期
        String codeKey = UUIDUtils.getUUID32();
        paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.EMAIL_TAG, codeKey, String.valueOf(user.getUserId()), 1800);

        List<String> values = new ArrayList<>();
        values.add(activeAccountUrl + codeKey);
        String content = EmailHelper.encapsulateText(values, "email_user_active.html");
        EmailHelper.sendMimeMessageMail(user.getEmail(), "PAX用户激活", content);*/
    }


    @Transactional
    @Override
    public void active(String verifyCode) {
        String userId = paxRedisTemplate.get(ConfigConstant.EMAIL_TAG, verifyCode);
        if (StringUtils.isNotBlank(userId)) {
            User user = userMapper.selectByPrimaryKey(Long.valueOf(userId));
            if("1".equals(user.getIsActive())){
                throw new BusinessException("该用户已激活，不能重复激活："+user.toString());
            }
            user.setIsActive("1");
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new TimeoutException(ConfigConstant.PAGE_SESSION_TIMEOUT, "激活链接已失效，请重新注册");
        }
    }

    //找回密码
    @Transactional
    @Override
    public void retrievePassword(String password, String verifyCode, String email) {
        User user = userMapper.findUserByAccountName(email);

        String md5Psw = null;
        try {
            md5Psw = DESUtil.decryption(password, verifyCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }

        //生成新盐值
        String salt = captchaProducer.createText();
        //与盐值混淆加密
        String pswSalt = DigestUtils.md5Hex(md5Psw + salt);
        user.setSalt(salt);
        user.setIsLocked("0");
        user.setPswErrorTimes(0);
        user.setPassword(pswSalt);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);

    }

    @Override
    public void modifyPassword(String accessToken,String oldPsw, String newPsw, String verifyCode) {
     /*   UserLoginInfo userInfo = paxRedisTemplate.get(ConfigConstant.USER_TAG,accessToken, new UserLoginInfo());
        //判断缓存里面是否存在该用户
        if(null == userInfo){
            logger.error("找不到该用户，token:[{}]",accessToken);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION,"系统异常");
        }*/
        Long userId = JwtTokenUtils.getAppUID(accessToken);//从token里面取出userid

        User user = userMapper.selectByPrimaryKey(userId);


        String oldMd5Psw = null;
        try {
            oldMd5Psw = DESUtil.decryption(oldPsw, verifyCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }

        //与盐值混淆加密
        String oldPswSalt = DigestUtils.md5Hex(oldMd5Psw + user.getSalt());

        //判断输入的旧密码和数据库旧密码是否一致
        if(!oldPswSalt.equals(user.getPassword())){
            throw new BusinessException(ConfigConstant.INVALID_USER_PASSWORD,"旧密码不正确");
        }

        String newMd5Psw = null;
        try {
            newMd5Psw = DESUtil.decryption(newPsw, verifyCode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SystemException(ConfigConstant.SYSTEM_EXCEPTION, "系统异常,请联系管理员");
        }
        //生成新盐值
        String salt = captchaProducer.createText();
        //与盐值混淆加密
        String newPswSalt = DigestUtils.md5Hex(newMd5Psw + salt);
        user.setSalt(salt);
        user.setPassword(newPswSalt);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    @Override
    public int updateErrTimes(User user) {
        return this.updateNotNull(user);
    }

    @Override
    public List<User> queryNotSendActiveEmailUser() {
        return userMapper.queryNotSendActiveEmailUser();
    }

    @Override
    public List<User> queryRestPswUser() {
        return userMapper.queryRestPswUser();
    }

}
