package com.pax.sms.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.pax.sms.constant
 *
 * @author yyyty
 * @time :  2018/6/12
 * @description:
 */
@Configuration
public class ConfigConstant {

    public static final String EMIAL_SUCCESS= "EMIAL-SUCCESS";
    public static final String SYSTEM_EXCEPTION= "SYSTEM-EXCEPTION";
    public static final String ACCESS_TOKEN= "access_token";
    public static final String INVALID_USER_PASSWORD= "INVALID-USER-PASSWORD";
    public static final String INVALID_VERIFY_CODE= "INVALID-VERIFY-CODE";
    public static final String INVALID_ACCOUNT_OR_PASSWORD= "INVALID-ACCOUNT-OR-PASSWORD";
    public static final String USER_BEEN_LOCKED= "USER-BEEN-LOCKED";
    public static final String PAGE_SESSION_TIMEOUT= "PAGE-SESSION-TIMEOUT";
    public static final String RESOURCE_UNAVAILABLE= "RESOURCE-UNAVAILABLE";


    public static final String VERIFY_CODE_TAG="verify_code";
    public static final String USER_TAG="user";
    public static final String CAPTCHA_TAG="captcha";
    public static final String EMAIL_TAG="email";
    public static final String JOB_TAG="job";
    public static final String IS_LOCKED="1";

    @Value("${spring.redis.session.timeout}")
    private int expire_seconds;

    @Value("${spring.filter.exlude.url}")
    private  String exlusive_urls;
    @Value("${server.servlet.context-path}")
    private  String projectPath;

    @Value("${spring.filter.exlude.static.resource}")
    private String exlusive_static_resource;

    public String getProjectPath() {
        return projectPath;
    }



    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public static String emailHost;

    public static String emailPort;

    public static String emailUserName;

    public static String emailPassword;

    public static String emailAddress;


    @Value("${mail.smtp.host}")
    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    @Value("${mail.smtp.port}")
    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    @Value("${mail.username}")
    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    @Value("${mail.password}")
    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    @Value("${mail.address}")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getExlusive_static_resource() {
        return exlusive_static_resource;
    }

    public void setExlusive_static_resource(String exlusive_static_resource) {
        this.exlusive_static_resource = exlusive_static_resource;
    }

    public String getExlusive_urls() {
        return exlusive_urls;
    }

    public void setExlusive_urls(String exlusive_urls) {
        this.exlusive_urls = exlusive_urls;
    }

    public int getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
    }
}
