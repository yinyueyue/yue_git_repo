package com.pax.sms.busi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomerSMSResult {
    private Long smsId;

    /**
     * 项目名称
     */
    private String projectName;

    private String emailContent;

    private String location;

    private Date sendTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    private Long sendAmount;

    private String targetPopilation;
    private String label;
    private Long hasSendAmount;
    private Long arrivedAmount;

    public Long getArrivedAmount() {
        return arrivedAmount;
    }

    public void setArrivedAmount(Long arrivedAmount) {
        this.arrivedAmount = arrivedAmount;
    }

    private Date actualSendTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai")
    public Date getActualSendTime() {
        return actualSendTime;
    }

    public void setActualSendTime(Date actualSendTime) {
        this.actualSendTime = actualSendTime;
    }

    public Long getHasSendAmount() {
        return hasSendAmount;
    }

    public void setHasSendAmount(Long hasSendAmount) {
        this.hasSendAmount = hasSendAmount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 审核状态：0未审核，1审核失败，2审核通过
     */
    private String auditStatus;

    /**
     * 发送状态，0，未发送，1已发送，
     */
    private String sendStatus;

    private Date createTime;

    private Date modifyTime;

    /**
     * 审核意见
     */
    private String auditMsg;

    /**
     * 充值条数
     */
    private Long chargeAmount;



    private Float successRate;

    /**
     * 从属哪个客户
     */
    private Long customerId;
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

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;

    private String regCompanyName;
    private String industry;
    private String email;
    private String isSendEmail;

    public String getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(String isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegCompanyName() {
        return regCompanyName;
    }

    public void setRegCompanyName(String regCompanyName) {
        this.regCompanyName = regCompanyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * @return sms_id
     */
    public Long getSmsId() {
        return smsId;
    }

    /**
     * @param smsId
     */
    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * @return email_content
     */
    public String getEmailContent() {
        return emailContent;
    }

    /**
     * @param emailContent
     */
    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent == null ? null : emailContent.trim();
    }

    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }


    public Long getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Long sendAmount) {
        this.sendAmount = sendAmount;
    }

    /**
     * @return target_popilation
     */
    public String getTargetPopilation() {
        return targetPopilation;
    }

    /**
     * @param targetPopilation
     */
    public void setTargetPopilation(String targetPopilation) {
        this.targetPopilation = targetPopilation == null ? null : targetPopilation.trim();
    }

    /**
     * 获取审核状态：0未审核，1审核失败，2审核通过
     *
     * @return audit_status - 审核状态：0未审核，1审核失败，2审核通过
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态：0未审核，1审核失败，2审核通过
     *
     * @param auditStatus 审核状态：0未审核，1审核失败，2审核通过
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    /**
     * 获取发送状态，0，未发送，1已发送，
     *
     * @return send_status - 发送状态，0，未发送，1已发送，
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置发送状态，0，未发送，1已发送，
     *
     * @param sendStatus 发送状态，0，未发送，1已发送，
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }



    /**
     * @return audit_msg
     */
    public String getAuditMsg() {
        return auditMsg;
    }

    /**
     * @param auditMsg
     */
    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg == null ? null : auditMsg.trim();
    }

    /**
     * 获取充值条数
     *
     * @return charge_amount - 充值条数
     */
    public Long getChargeAmount() {
        return chargeAmount;
    }

    /**
     * 设置充值条数
     *
     * @param chargeAmount 充值条数
     */
    public void setChargeAmount(Long chargeAmount) {
        this.chargeAmount = chargeAmount;
    }


    /**
     * @return success_rate
     */
    public Float getSuccessRate() {
        return successRate;
    }

    /**
     * @param successRate
     */
    public void setSuccessRate(Float successRate) {
        this.successRate = successRate;
    }

    /**
     * 获取从属哪个客户
     *
     * @return customer_id - 从属哪个客户
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置从属哪个客户
     *
     * @param customerId 从属哪个客户
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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