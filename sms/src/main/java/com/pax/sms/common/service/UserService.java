package com.pax.sms.common.service;

import com.alibaba.fastjson.JSONObject;
import com.pax.sms.common.domain.User;
import com.pax.sms.common.model.AddUserInput;

import java.util.List;

/**
 * com.pax.sms.comon.service
 *
 * @author yyyty
 * @time :  2018/6/21
 * @description:
 */
public interface UserService extends IService<User>{

    JSONObject login(String accountName, String password, String veryfyCode, String verifyCodeKey);

    void register(AddUserInput user);

    void active(String verifyCode);

    void retrievePassword(String password,String verifyCode,String email);//重置密码

    void modifyPassword(String accessToken,String oldPsw,String newPsw,String verifyCode);

    int updateErrTimes(User user);

    List<User> queryNotSendActiveEmailUser();

    List<User> queryRestPswUser();
}
