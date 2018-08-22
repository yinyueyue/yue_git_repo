package com.pax.sms.busi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pax.sms.busi.domain.CustomerSMS;
import com.pax.sms.busi.model.CustomerSMSInput;
import com.pax.sms.busi.model.CustomerSMSResult;
import com.pax.sms.busi.service.CustomerSmsService;
import com.pax.sms.common.model.UserLoginInfo;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.AjaxObject;
import com.pax.sms.core.utils.Page;
import com.pax.sms.core.utils.ScopeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * com.pax.sms.busi.controller
 *
 * @author yyyty
 * @time :  2018/6/27
 * @description:
 */
@RestController
@RequestMapping("/msg")
public class CustomerSmsController {
    @Autowired
    private PaxRedisTemplate paxRedisTemplate;


    @Autowired
    private CustomerSmsService customerSmsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxObject save(CustomerSMSInput customerSMSInput) {

     //   long sendTimeStamp = customerSMSInput.getSendTime();
        CustomerSMS customerSMS = new CustomerSMS();
        BeanUtils.copyProperties(customerSMSInput, customerSMS);
 //       customerSMS.setSendTime(sendTimeStamp);
        customerSMS.setCreateTime(new Date());
        if(null != customerSMS.getSmsId() && StringUtils.isNotBlank(String.valueOf(customerSMS.getSmsId()))){
            customerSmsService.updateNotNull(customerSMS);
        }else{
            customerSmsService.saveNotNull(customerSMS);
        }
        JSONObject data = new JSONObject();
        data.put("smsId",customerSMS.getSmsId());
        return AjaxObject.okMsgData("保存成功",data);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public AjaxObject modify(CustomerSMSInput customerSMSInput) {
    //    long sendTimeStamp = customerSMSInput.getSendTime();
        CustomerSMS customerSMS = new CustomerSMS();
        BeanUtils.copyProperties(customerSMSInput, customerSMS);

   //     customerSMS.setSendTime(sendTimeStamp);
        customerSMS.setModifyTime(new Date());
        customerSMS.setAuditStatus("0");
        customerSMS.setAuditMsg(null);
        customerSmsService.updateNotNull(customerSMS);
        return AjaxObject.okMsg("修改成功");
    }
    @RequestMapping(value = "/audit/modify", method = RequestMethod.POST)
    public AjaxObject auditModify(CustomerSMSInput customerSMSInput) {
        CustomerSMS customerSMS = new CustomerSMS();
        BeanUtils.copyProperties(customerSMSInput, customerSMS);

        long sendAmount = customerSMS.getHasSendAmount();  //已发送条数
        long arrivedAmout =  customerSMS.getArrivedAmount(); //到达条数

        Float successRate = 0.00f;
        if(sendAmount>0L ){
            successRate = new BigDecimal(arrivedAmout).multiply(new BigDecimal(100)).divide(new BigDecimal(sendAmount),2,RoundingMode.HALF_UP).floatValue();
        }
        customerSMS.setSuccessRate(successRate);
        customerSMS.setModifyTime(new Date());
        customerSmsService.updateNotNull(customerSMS);
        return AjaxObject.okMsg("修改成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxObject delete(String smsId) {
        customerSmsService.delete(smsId);
        return AjaxObject.okMsg("删除成功");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)//@RequestBody
    public AjaxObject list(Page page, String companyName, String projectName, String auditStatus) {
        page.getCondition().put("auditStatus",auditStatus);
        page.getCondition().put("companyName",companyName);
        page.getCondition().put("projectName",projectName);
        UserLoginInfo userInfo = paxRedisTemplate.get(ConfigConstant.USER_TAG, String.valueOf(ScopeUtils.getUserId()), new UserLoginInfo());
        if(!"1".equals(userInfo.getRole())){
            page.getCondition().put("userId", ScopeUtils.getUserId());
        }
        List<CustomerSMSResult> customerSmsList = customerSmsService.querySmsInfo(page);
        PageInfo<CustomerSMSResult> pageInfo = new PageInfo<>(customerSmsList);
        return AjaxObject.pageOk(pageInfo);
    }
//method = RequestMethod.GET,
    @RequestMapping(value = "/audit/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AjaxObject adminlist(Page page,String companyName,String projectName) {
        page.getCondition().put("auditStatus","1");
        page.getCondition().put("companyName",companyName);
        page.getCondition().put("projectName",projectName);
        List<CustomerSMSResult> customerSmsList = customerSmsService.querySmsInfo(page);
        PageInfo<CustomerSMSResult> pageInfo = new PageInfo<>(customerSmsList);
        return AjaxObject.pageOk(pageInfo);
    }

    @RequestMapping(value = "/audit/detail", method = RequestMethod.GET)
    public AjaxObject querySmsDetail(String smsId) {
        CustomerSMS customerSMS = customerSmsService.selectByKey(smsId);
        CustomerSMSResult result = new CustomerSMSResult();
        BeanUtils.copyProperties(customerSMS,result);
        return AjaxObject.okData(result);
    }

    @RequestMapping(value = "/audit/comfirm", method = RequestMethod.POST)
    public AjaxObject audit(Long smsId,String auditMsg,String auditStatus) {

        CustomerSMS customerSMS = new CustomerSMS();
        customerSMS.setSmsId(smsId);
        customerSMS.setAuditMsg(auditMsg);
        customerSMS.setAuditStatus(auditStatus);
        customerSMS.setAuditTime(new Date());
        customerSMS.setIsSendEmail("1");

        customerSmsService.updateNotNull(customerSMS);
        return AjaxObject.okMsg("审核成功");
    }

    @RequestMapping(value = "/toAudit")//, method = RequestMethod.POST
    public AjaxObject submitSmsToAudit(CustomerSMSInput customerSMSInput) {
//        long sendTimeStamp = customerSMSInput.getSendTime();
        CustomerSMS customerSMS = new CustomerSMS();
        BeanUtils.copyProperties(customerSMSInput, customerSMS);
  //      customerSMS.setSendTime(sendTimeStamp);
        customerSMS.setAuditMsg(null);
        customerSMS.setAuditStatus("1");
        if(null !=customerSMSInput.getSmsId() &&  StringUtils.isNotBlank(String.valueOf(customerSMSInput.getSmsId()))){
            customerSmsService.updateNotNull(customerSMS);
        }else{
            customerSmsService.saveNotNull(customerSMS);
        }

        return AjaxObject.okMsg("提交审核成功");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public AjaxObject getSmsToAudit() {
        CustomerSMSResult result = customerSmsService.getSmsToAudit(ScopeUtils.getUserId());
        Date date = new Date();
        return AjaxObject.okData(result);
    }

}
