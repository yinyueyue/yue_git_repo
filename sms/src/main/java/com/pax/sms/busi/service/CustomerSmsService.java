package com.pax.sms.busi.service;

import com.pax.sms.busi.domain.CustomerSMS;
import com.pax.sms.busi.model.CustomerSMSResult;
import com.pax.sms.common.service.IService;
import com.pax.sms.common.service.impl.BaseService;
import com.pax.sms.core.utils.Page;

import java.util.List;

/**
 * com.pax.sms.busi.service
 *
 * @author yyyty
 * @time :  2018/6/27
 * @description:
 */
public interface CustomerSmsService extends IService<CustomerSMS> {

    int deleteByCustomerId(String customerId);

   // List<CustomerSMS> queryByCustomerId(Page page);

    List<CustomerSMSResult> querySmsInfo(Page page);


    List<CustomerSMSResult> queryNotAuditSms(Page page);

    CustomerSMSResult getSmsToAudit(Long userId);

    List<CustomerSMSResult> querySmsToSendEmail();


}
