package com.pax.sms.busi.job;

import com.pax.sms.busi.domain.CustomerSMS;
import com.pax.sms.busi.model.CustomerSMSResult;
import com.pax.sms.busi.service.CustomerSmsService;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.EmailHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * com.pax.sms.busi.job
 *
 * @author yyyty
 * @time :  2018/7/12
 * @description: 发送审核结果邮件
 */
@Component
public class SendEmailAuditMsgJob {

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${static.resource.img.logo}")
    private String logoUrl;
    @Value("${active_user_account_success_url}")
    private String indexUrl;

    @Autowired
    private CustomerSmsService customerSmsService;
    @Scheduled(fixedRate = 60000)
    public void sendMsgAuditEmail(){
        //判断是否有定时任务执行
        String hasJob = paxRedisTemplate.get(ConfigConstant.JOB_TAG,"sms_send_audit_msg_job");
        if(StringUtils.isBlank(hasJob)){
            //有未激活用户且，无定时任务执行，则创建，
            paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.JOB_TAG,"sms_send_audit_msg_job","true",60);
            List<CustomerSMSResult> emails = customerSmsService.querySmsToSendEmail();

            for(CustomerSMSResult emailInfo :emails){
                //修改发送邮件标注，下次不再扫描
                CustomerSMS sms = new CustomerSMS();
                sms.setSmsId(emailInfo.getSmsId());
                sms.setIsSendEmail("0");
                customerSmsService.updateNotNull(sms);

                List<String> values = new ArrayList<>();
                values.add(indexUrl);
                values.add(logoUrl);
                if("2".equals(emailInfo.getAuditStatus())){
                    values.add("失败");
                }else {
                    values.add("通过");
                }
                values.add(emailInfo.getRegCompanyName());
                values.add(emailInfo.getProjectName());

                if("2".equals(emailInfo.getAuditStatus())){
                    values.add("失败");
                }else {
                    values.add("通过");
                }

                values.add(indexUrl);
                values.add(indexUrl);
                values.add(indexUrl);

                String content = EmailHelper.encapsulateText(values, "email_sms_audit_result.html");
                EmailHelper.sendMimeMessageMail(emailInfo.getEmail(), "短信审核", content,"短信服务平台");

            }
            paxRedisTemplate.deleteWithPrefix(ConfigConstant.JOB_TAG,"sms_send_audit_msg_job");
        }
    }
}
