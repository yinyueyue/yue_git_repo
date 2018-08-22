package com.pax.sms.busi.dao;

import com.pax.sms.busi.domain.CustomerSMS;
import com.pax.sms.busi.model.CustomerSMSResult;
import com.pax.sms.core.utils.EntityMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerSMSMapper extends EntityMapper<CustomerSMS> {
    int deleteByCustomerId(@Param("customerId") String customerId);

    List<CustomerSMS> queryByCustomerId(Map<String,Object> condition);

    List<CustomerSMSResult> querySmsInfo(Map<String,Object> condition);

    List<CustomerSMSResult> queryNotAuditSms(Map<String,Object> condition);

    List<CustomerSMSResult> querySmsToSendEmail();

    CustomerSMSResult queryNotAuditMsg(@Param("userId") Long userId);



}