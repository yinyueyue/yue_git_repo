package com.pax.sms.common.controller;

import com.pax.sms.common.dao.UserMapper;
import com.pax.sms.common.domain.User;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.AjaxObject;
import com.pax.sms.core.utils.EmailHelper;
import com.pax.sms.core.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * com.pax.sms.common.controller
 *
 * @author yyyty
 * @time :  2018/6/25
 * @description:
 */


@RestController
public class RedisEmailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${active_user_reset_psw_forward_url}")
    private String resetPswUrl;

    @Value("${static.resource.img.logo}")
    private String logoUrl;
    @Value("${active_user_account_success_url}")
    private String indexUrl;

    @Resource
    private UserMapper userMapper;

    @RequestMapping(value = "/email/resetUserPsw",method = RequestMethod.GET)
    public AjaxObject sendVerifyCodeEmail(String emailAddress,
                                          HttpServletResponse response){
        User user = userMapper.findUserByAccountName(emailAddress);
        if(null == user){
            return AjaxObject.errorMsg("该用户不存在,请检查邮件地址是否正确");
        }
        if("0".equals(user.getIsActive())){
            return AjaxObject.errorMsg("该用户还未激活,若忘记密码请重新注册");
        }
        user.setIsSendEmail("1");
        userMapper.updateByPrimaryKeySelective(user);
        return AjaxObject.okCodeMsg(ConfigConstant.EMIAL_SUCCESS,"邮件发送成功,请查收");
    }

    @RequestMapping(value = "/genernateVerifyCode" ,method = RequestMethod.GET)
    public AjaxObject genernateVerifyCode(){
        String codeKey= UUIDUtils.getUUID32();
        paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.VERIFY_CODE_TAG,codeKey, codeKey, 1800);

        return AjaxObject.okData(codeKey);
    }
}
