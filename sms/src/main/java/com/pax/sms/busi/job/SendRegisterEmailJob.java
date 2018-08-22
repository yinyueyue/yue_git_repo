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

/**
 * com.pax.sms.busi.job
 *
 * @author yyyty
 * @time :  2018/7/11
 * @description: 用户注册发送激活邮件定时任务
 */
@Component
public class SendRegisterEmailJob {
    @Value("${active_user_account_email_url}")
    private String activeAccountUrl;

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${static.resource.img.logo}")
    private String logoUrl;
    @Value("${active_user_account_success_url}")
    private String indexUrl;

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 60000)
    public void sendRegitserEmail(){
        //判断是否有定时任务执行，判断锁是否存在
        String hasJob = paxRedisTemplate.get(ConfigConstant.JOB_TAG,"user_regitser_job");
        if(StringUtils.isBlank(hasJob)){
            //有未激活用户且，无定时任务执行，则创建锁，其他服务器不能进入，
            paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.JOB_TAG,"user_regitser_job","true",60);
            List<User> users = userService.queryNotSendActiveEmailUser();
            for(User user :users){

                user.setIsSendEmail("0");
                userService.updateNotNull(user);
                //生成一个随机码，存到缓存，作为用户激活的key，30分钟过期
                String codeKey = UUIDUtils.getUUID32();
                paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.EMAIL_TAG, codeKey, String.valueOf(user.getUserId()), 1800);

                List<String> values = new ArrayList<>();
                values.add(indexUrl);
                values.add(logoUrl);
                values.add(activeAccountUrl + codeKey);
                values.add(activeAccountUrl + codeKey);
                values.add(activeAccountUrl + codeKey);
              //  String content = EmailHelper.encapsulateText(values, "email_user_active.html");
                String content = EmailHelper.encapsulateText(values, "email_user_active.html");
                EmailHelper.sendMimeMessageMail(user.getEmail(), "商户短信服务平台用户激活", content,"短信服务平台");

            }

            //删除任务，释放锁
            paxRedisTemplate.deleteWithPrefix(ConfigConstant.JOB_TAG,"user_regitser_job");
        }

    }
}
