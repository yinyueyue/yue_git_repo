package com.pax.sms.busi.job;

import com.pax.sms.common.dao.UserMapper;
import com.pax.sms.common.domain.User;
import com.pax.sms.common.service.UserService;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.EmailHelper;
import com.pax.sms.core.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.pax.sms.constant.ConfigConstant.emailAddress;

/**
 * com.pax.sms.busi.job
 *
 * @author yyyty
 * @time :  2018/7/23
 * @description:
 */
@Component
public class SendRestPswEmailJob {
    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${active_user_reset_psw_forward_url}")
    private String resetPswUrl;

    @Value("${static.resource.img.logo}")
    private String logoUrl;
    @Value("${active_user_account_success_url}")
    private String indexUrl;

    @Autowired
    private UserService userService;


    @Scheduled(fixedRate = 60000)
    public void sendResetPswEmail() {
        paxRedisTemplate.deleteWithPrefix(ConfigConstant.JOB_TAG,"sms_send_reset_psw_job");

        //判断是否有定时任务执行
        String hasJob = paxRedisTemplate.get(ConfigConstant.JOB_TAG, "sms_send_reset_psw_job");

        if (StringUtils.isBlank(hasJob)) {
            //有未激活用户且，无定时任务执行，则创建，
            paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.JOB_TAG, "sms_send_reset_psw_job", "true", 60);
            List<User> users = userService.queryRestPswUser();

            for (User user :users){

                user.setIsSendEmail("0");  //修改发送邮件状态，下次不再扫描
                userService.updateNotNull(user);
                String codeKey = UUIDUtils.getUUID32();
                paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.EMAIL_TAG, codeKey, user.getEmail(), 30 * 60);

                List<String> values = new ArrayList<>();
                values.add(indexUrl);
                values.add(logoUrl);
                values.add(resetPswUrl + codeKey);
                values.add(resetPswUrl + codeKey);
                values.add(resetPswUrl + codeKey);

                String content = EmailHelper.encapsulateText(values, "emai_user_reset_password.html");
                EmailHelper.sendMimeMessageMail(user.getEmail(), "用户找回密码", content,"短信服务平台");
            }

            paxRedisTemplate.deleteWithPrefix(ConfigConstant.JOB_TAG,"sms_send_reset_psw_job");
        }
    }
}
