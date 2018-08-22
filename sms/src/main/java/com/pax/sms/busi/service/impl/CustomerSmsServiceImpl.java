package com.pax.sms.busi.service.impl;

import com.github.pagehelper.PageHelper;
import com.pax.sms.busi.dao.CustomerSMSMapper;
import com.pax.sms.busi.domain.CustomerSMS;
import com.pax.sms.busi.model.CustomerSMSResult;
import com.pax.sms.busi.service.CustomerSmsService;
import com.pax.sms.common.service.impl.BaseService;
import com.pax.sms.core.utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.pax.sms.busi.service.impl
 *
 * @author yyyty
 * @time :  2018/6/27
 * @description:
 */
@Service
public class CustomerSmsServiceImpl  extends BaseService<CustomerSMS> implements CustomerSmsService {
    @Resource
    private CustomerSMSMapper customerSMSMapper;
    @Transactional
    @Override
    public int deleteByCustomerId(String customerId) {
        return customerSMSMapper.deleteByCustomerId(customerId);
    }

/*    @Override
    public List<CustomerSMS> queryByCustomerId(Page page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        return customerSMSMapper.queryByCustomerId(page.getCondition());
    }*/

    @Override
    public List<CustomerSMSResult> querySmsInfo(Page page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        return customerSMSMapper.querySmsInfo(page.getCondition());
    }

    @Override
    public List<CustomerSMSResult> queryNotAuditSms(Page page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        return customerSMSMapper.queryNotAuditSms(page.getCondition());
    }

    @Override
    public CustomerSMSResult getSmsToAudit(Long userId) {
        return customerSMSMapper.queryNotAuditMsg(userId);
    }

    @Override
    public List<CustomerSMSResult> querySmsToSendEmail() {
        return customerSMSMapper.querySmsToSendEmail();
    }


}
