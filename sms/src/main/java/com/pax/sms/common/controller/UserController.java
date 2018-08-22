package com.pax.sms.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.pax.sms.common.model.AddUserInput;
import com.pax.sms.common.model.UserLoginInfo;
import com.pax.sms.common.service.UserService;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.exception.BusinessException;
import com.pax.sms.core.exception.SystemException;
import com.pax.sms.core.exception.TimeoutException;
import com.pax.sms.core.utils.AjaxObject;
import com.pax.sms.core.utils.BindingResultHandler;
import com.pax.sms.core.utils.JwtTokenUtils;
import com.pax.sms.core.utils.ScopeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * com.pax.sms.common.controller
 *
 * @author yyyty
 * @time :  2018/6/25
 * @description:
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${active_user_account_success_url}")
    private String acive_status_url;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxObject userLogin( String accountName, String password, String verifyCode, String verifyCodeKey) {
        try {
            JSONObject jsonObject = userService.login(accountName, password, verifyCode, verifyCodeKey);
            logger.info("login info [{}]",jsonObject);
            return AjaxObject.okData(jsonObject.toJSONString());
        } catch (BusinessException e) {
            logger.info("login info [{}]",e.getMessage());
            return AjaxObject.errorCodeMsg(e.getErrorCode(), e.getMessage());
        } catch (SystemException e) {
            logger.info("login info [{}]",e.getMessage());
            return AjaxObject.errorMsg( e.getMessage());
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AjaxObject userRegister(AddUserInput input, BindingResult br) {
    /*    if (br.hasErrors()) {
            JSONObject errorJson = BindingResultHandler.handleBindingResult(br);
            return AjaxObject.errorMsg(errorJson.toString());
        }*/

        String verifyCode = paxRedisTemplate.get(ConfigConstant.VERIFY_CODE_TAG, input.getVerifyCode());
        if (verifyCode == null || !verifyCode.equals(input.getVerifyCode())) {
            return AjaxObject.errorCodeMsg(ConfigConstant.PAGE_SESSION_TIMEOUT, "当前页面已经过期,请刷新页面");
        }
        try {
            userService.register(input);
        } catch (BusinessException | SystemException e) {
            logger.error(e.getMessage(), e);

            return AjaxObject.errorMsg(e.getMessage());
        }
        return AjaxObject.okCodeMsg("REGISTER-USER-SUCCESS", "注册成功");

    }

    @RequestMapping("/active")
    public void userActive(String verifyCode, HttpServletResponse response) {
        try {
            userService.active(verifyCode);
            response.sendRedirect(acive_status_url + "?status=activeSuccess");
            //response.sendRedirect("http://xxx?status=session-time-out");
        } catch (BusinessException e) {
            try {
                response.sendRedirect(acive_status_url);
            } catch (Exception e1) {
                logger.error(e.getMessage(), e);
            }
            logger.error(e.getMessage(), e);
        } catch (TimeoutException e) {
            try {
                response.sendRedirect(acive_status_url + "?status=ativeFail");
            } catch (Exception e1) {
                logger.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            try {
                response.sendRedirect(acive_status_url + "?status=ativeFail");
            } catch (Exception e1) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    @Value("${active_user_reset_psw_url}")
    private String resetPswUrl;

    //点击邮件地址，重定向到前端页面地址，目的是进行code是否过期判断
    @RequestMapping("/pswForward")
    public void pswForward(String verifyCode, HttpServletResponse response) {
        String email = paxRedisTemplate.get(ConfigConstant.EMAIL_TAG, verifyCode);
        if (StringUtils.isNotBlank(email)) {
            try {
                response.sendRedirect(resetPswUrl + verifyCode);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            try {
                response.sendRedirect(acive_status_url + "?status=timeout");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    @RequestMapping(value = "/retrievePsw",method = RequestMethod.POST)
    public AjaxObject retrievePassword(String password, String verifyCode, HttpServletResponse response) {
        String email = paxRedisTemplate.get(ConfigConstant.EMAIL_TAG, verifyCode);

        if (StringUtils.isBlank(email)) {
            return AjaxObject.errorMsg("重置密码链接已经过期");
        }
        userService.retrievePassword(password, verifyCode, email);
        return AjaxObject.okMsg("重置密码成功");
    }

    @RequestMapping(value = "/modifyPsw",method = RequestMethod.POST)
    public AjaxObject modifyPsw(String accessToken,String oldPsw, String newPsw, String verifyCode){
        try{
            userService.modifyPassword(accessToken,oldPsw,newPsw,verifyCode);
            return AjaxObject.okMsg("修改密码成功");
        }catch(BusinessException | SystemException e ){
            logger.error(e.getMessage(),e);
            return AjaxObject.errorMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public AjaxObject logout(){
        paxRedisTemplate.deleteWithPrefix(ConfigConstant.USER_TAG, String.valueOf(ScopeUtils.getUserId()));
        return AjaxObject.okMsg("退出成功");
    }

    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public AjaxObject getLoginUserInfo(){
        try{
            Long userId = JwtTokenUtils.getAppUID(ScopeUtils.getAccessToken());
            UserLoginInfo userInfo = paxRedisTemplate.get(ConfigConstant.USER_TAG, String.valueOf(userId), new UserLoginInfo());

            logger.info("user info : [{}]",userInfo);
            return AjaxObject.okData(userInfo);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return AjaxObject.errorMsg("系统异常");
        }

    }


}
