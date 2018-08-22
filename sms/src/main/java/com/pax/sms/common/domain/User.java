package com.pax.sms.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_b_user")
public class User {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "account_name")
    private String accountName;

    private String email;

    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime = new Date();

    /**
     * 最后修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime = new Date();

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "psw_error_times")
    private Integer pswErrorTimes;

    /**
     * 0未锁定，1锁定
     */
    @Column(name = "is_locked")
    private String isLocked;

    @Column(name = "phone_no")
    private String phoneNo;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 激活状态，0未激活，1激活
     */
    @Column(name = "is_active")
    private String isActive;

    @Column(name = "is_send_email")
    private String isSendEmail;

    public String getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(String isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return account_name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取盐值
     *
     * @return salt - 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     *
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后修改时间
     *
     * @return modify_time - 最后修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param modifyTime 最后修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return psw_error_times
     */
    public Integer getPswErrorTimes() {
        return pswErrorTimes;
    }

    /**
     * @param pswErrorTimes
     */
    public void setPswErrorTimes(Integer pswErrorTimes) {
        this.pswErrorTimes = pswErrorTimes;
    }

    /**
     * 获取0未锁定，1锁定
     *
     * @return is_locked - 0未锁定，1锁定
     */
    public String getIsLocked() {
        return isLocked;
    }

    /**
     * 设置0未锁定，1锁定
     *
     * @param isLocked 0未锁定，1锁定
     */
    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked == null ? null : isLocked.trim();
    }

    /**
     * @return phone_no
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    /**
     * @return ext1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * @param ext1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * @return ext2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * @param ext2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * @return ext3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * @param ext3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    /**
     * @return ext4
     */
    public String getExt4() {
        return ext4;
    }

    /**
     * @param ext4
     */
    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    /**
     * @return ext5
     */
    public String getExt5() {
        return ext5;
    }

    /**
     * @param ext5
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

    /**
     * 获取激活状态，0未激活，1激活
     *
     * @return is_active - 激活状态，0未激活，1激活
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * 设置激活状态，0未激活，1激活
     *
     * @param isActive 激活状态，0未激活，1激活
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", lastLoginTime=" + lastLoginTime +
                ", pswErrorTimes=" + pswErrorTimes +
                ", isLocked='" + isLocked + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", ext1='" + ext1 + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                ", ext4='" + ext4 + '\'' +
                ", ext5='" + ext5 + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}