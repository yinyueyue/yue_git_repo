package com.pax.sms.core.config;

import com.pax.sms.core.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;


/**
 * com.pax.sms.core.config
 * 使用spring来管理filter的创建
 *
 * @author yyyty
 * @time :  2018/7/2
 * @description:
 */
@Configuration
public class FilterConfig {

    @Bean
    public Filter tokenFilter() {
        return new TokenFilter();
    }
    @Bean
    public FilterRegistrationBean uploadFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //registration.setFilter(new DelegatingFilterProxy("tokenFilter"));  //使用该方法注册，init不会执行
        registration.setFilter(tokenFilter());
        registration.addUrlPatterns("/*");
        registration.setName("tokenFilter");
        registration.setOrder(1);
        return registration;
    }

}
