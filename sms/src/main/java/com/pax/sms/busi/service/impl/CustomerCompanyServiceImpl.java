package com.pax.sms.busi.service.impl;

import com.github.pagehelper.PageHelper;
import com.pax.sms.busi.dao.CustomerCompanyMapper;
import com.pax.sms.busi.domain.CustomerCompany;
import com.pax.sms.busi.service.CustomerCompanyService;
import com.pax.sms.common.service.impl.BaseService;
import com.pax.sms.core.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.pax.sms.busi.service.impl
 *
 * @author yyyty
 * @time :  2018/6/28
 * @description:
 */
@Service
public class CustomerCompanyServiceImpl extends BaseService<CustomerCompany> implements CustomerCompanyService {
    @Resource
    private CustomerCompanyMapper customerCompanyMapper;
    @Override
    public List<CustomerCompany> queryCustomerCompanyList(Page page) {
     //   PageHelper.startPage(page.getPage(), page.getRows());
        return customerCompanyMapper.queryCustomerCompanyList(page.getCondition());
    }
}
