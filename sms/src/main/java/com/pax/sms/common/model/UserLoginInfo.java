package com.pax.sms.common.model;

import java.io.Serializable;

/**
 * com.pax.sms.comon.domain
 *
 * @author yyyty
 * @time :  2018/6/20
 * @description:
 */
public class UserLoginInfo implements Serializable{
    private long userId;
    private String accountName;
    private String lastLoginTime;
    private String token;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "userId=" + userId +
                ", userName='" + accountName + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
