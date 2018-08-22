package com.pax.sms.busi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_b_customer_company")
public class CustomerCompany {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    /**
     * 注册客户公司
     */
    @Column(name = "reg_company_name")
    private String regCompanyName;

    /**
     * 客户名字
     */
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;

    /**
     * 行业类别
     */
    private String industry;

    /**
     * 从属哪个用户
     */
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;

    /**
     * @return customer_id
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取注册客户公司
     *
     * @return reg_company_name - 注册客户公司
     */
    public String getRegCompanyName() {
        return regCompanyName;
    }

    /**
     * 设置注册客户公司
     *
     * @param regCompanyName 注册客户公司
     */
    public void setRegCompanyName(String regCompanyName) {
        this.regCompanyName = regCompanyName == null ? null : regCompanyName.trim();
    }

    /**
     * 获取客户名字
     *
     * @return customer_name - 客户名字
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户名字
     *
     * @param customerName 客户名字
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * @return contact_number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null ? null : contactNumber.trim();
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
     * 获取行业类别
     *
     * @return industry - 行业类别
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置行业类别
     *
     * @param industry 行业类别
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * 获取从属哪个用户
     *
     * @return user_id - 从属哪个用户
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置从属哪个用户
     *
     * @param userId 从属哪个用户
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
}