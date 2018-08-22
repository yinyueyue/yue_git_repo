package com.pax.sms.busi.model;

import java.util.Date;

public class CustomerSMSInput {
    private Long smsId;

    /**
     * 项目名称
     */
    private String projectName;

    private String emailContent;

    private String location;

    private Date sendTime;

    private Long sendAmount;

    private String targetPopilation;

    /**
     * 审核状态：0未审核，2审核失败，3审核通过
     */
    private String auditStatus;


    /**
     * 审核意见
     */
    private String auditMsg;

    private String label;

    /**
     * 充值条数
     */
    private Long chargeAmount;
    /**
     * 充值条数
     */
    private Long hasSendAmount;
    private Long arrivedAmount;

    public Long getArrivedAmount() {
        return arrivedAmount;
    }

    public void setArrivedAmount(Long arrivedAmount) {
        this.arrivedAmount = arrivedAmount;
    }

    private Long sendStatus;

    private Date actualSendTime;

    public Long getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Long sendStatus) {
        this.sendStatus = sendStatus;
    }

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

    private Float successRate;

    public Long getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Long chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Float successRate) {
        this.successRate = successRate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }



    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    private long customerId;


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return send_time
     */


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




}