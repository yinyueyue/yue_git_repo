package com.pax.sms.common.domain;

/**
 * com.pax.sms.comon.domain
 *
 * @author yyyty
 * @time :  2018/6/14
 * @description:
 */
public class TokenModel {

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
