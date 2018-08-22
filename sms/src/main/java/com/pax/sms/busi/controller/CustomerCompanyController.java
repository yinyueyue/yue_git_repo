package com.pax.sms.busi.controller;

import com.pax.sms.busi.domain.CustomerCompany;
import com.pax.sms.busi.model.CustomerCompanyAddInput;
import com.pax.sms.busi.service.CustomerCompanyService;
import com.pax.sms.busi.service.CustomerSmsService;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.AjaxObject;
import com.pax.sms.core.utils.Page;
import com.pax.sms.core.utils.ScopeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * com.pax.sms.busi.controller
 *
 * @author yyyty
 * @time :  2018/6/28
 * @description:
 */

@RestController
@RequestMapping("/company")
public class CustomerCompanyController{


    @Autowired
    private CustomerCompanyService customerCompanyService;

    @Autowired
    private CustomerSmsService customerSmsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxObject save(CustomerCompanyAddInput input) {
        CustomerCompany customerCompany = new CustomerCompany();
        BeanUtils.copyProperties(input, customerCompany);
        customerCompany.setCreateTime(new Date());

        customerCompany.setUserId(ScopeUtils.getUserId());
        customerCompanyService.saveNotNull(customerCompany);
        return AjaxObject.okMsg("新增成功");
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public AjaxObject modify(CustomerCompanyAddInput input) {
        CustomerCompany customerCompany = new CustomerCompany();
        BeanUtils.copyProperties(input, customerCompany);
        customerCompany.setCreateTime(new Date());
        customerCompanyService.updateNotNull(customerCompany);
        return AjaxObject.okMsg("修改成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxObject delete(String customerId) {
        String[] ids = customerId.split(",");
        for (int i = 0; i < ids.length; i++) {
            customerCompanyService.delete(ids[i]);

            customerSmsService.deleteByCustomerId(ids[i]);
        }
        return AjaxObject.okMsg("删除成功");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxObject list( Page page) {

        page.getCondition().put("userId", ScopeUtils.getUserId());
        List<CustomerCompany> customerCompanyList = customerCompanyService.queryCustomerCompanyList(page);
      //  PageInfo<CustomerCompany> pageInfo = new PageInfo<>(customerCompanyList);
        return AjaxObject.okData(customerCompanyList);
    }




}
