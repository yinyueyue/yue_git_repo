package com.pax.sms.busi.dao;

import com.pax.sms.busi.domain.CustomerCompany;
import com.pax.sms.core.utils.EntityMapper;

import java.util.List;
import java.util.Map;

public interface CustomerCompanyMapper extends EntityMapper<CustomerCompany> {

    List<CustomerCompany> queryCustomerCompanyList(Map<String, Object> condition);
}