package com.pax.sms.busi.service;

import com.pax.sms.busi.domain.CustomerCompany;
import com.pax.sms.common.service.IService;
import com.pax.sms.core.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * com.pax.sms.busi.service
 *
 * @author yyyty
 * @time :  2018/6/28
 * @description:
 */
public interface CustomerCompanyService extends IService<CustomerCompany> {

    List<CustomerCompany> queryCustomerCompanyList(Page page);
}
