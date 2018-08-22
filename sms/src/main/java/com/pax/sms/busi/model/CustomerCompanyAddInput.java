package com.pax.sms.busi.model;

/**
 * com.pax.sms.busi.model
 *
 * @author yyyty
 * @time :  2018/6/27
 * @description:
 */
public class CustomerCompanyAddInput {
    private String regCompanyName;

    private String customerName;

    private String contactNumber;

    private String email;

    private String industry;
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getRegCompanyName() {
        return regCompanyName;
    }

    public void setRegCompanyName(String regCompanyName) {
        this.regCompanyName = regCompanyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
