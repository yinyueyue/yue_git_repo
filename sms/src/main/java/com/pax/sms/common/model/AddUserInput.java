package com.pax.sms.common.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * com.pax.sms.common.model
 *
 * @author yyyty
 * @time :  2018/6/25
 * @description:
 */
public class AddUserInput {

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    @Length(max = 50,message = "账户最大长度为50")
    @NotEmpty(message = "账户不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{4,20}$", message = "登录名格式不正确")
    @Column(name = "account_name")
    private String accountName;

    @Length(max = 100,message = "邮箱最大长度为100")
    @NotEmpty(message = "电子邮箱不能为空")
    @Email(message = "电子邮箱格式不正确")
    private String email;


    @NotEmpty(message = "密码不能为空")
   // @Pattern(regexp = "^[\\w]{8,20}", message = "密码格式不正确")
    private String password;

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Pattern(regexp = "(^(0[0-9]{2,3}[\\-]{0,1})?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$)|(^0?[1][0-9]{10}$)",message = "电话号码格式不正确")
    @Column(name = "phone_no")
    private String phoneNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
