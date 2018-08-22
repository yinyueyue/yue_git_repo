package com.pax.sms.core.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * com.pax.sms.config
 *
 * @author yyyty
 * @time :  2018/6/20
 * @description:
 */
@Configuration
public class CaptchaConfig {
    @Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "no");
        //properties.setProperty("kaptcha.border.thickness", "1");
        properties.setProperty("kaptcha.border.color", "94,200,236");
        properties.setProperty("kaptcha.textproducer.font.color", "gray");
        properties.setProperty("kaptcha.noise.color", "gray");
        properties.setProperty("kaptcha.image.width", "135");
        properties.setProperty("kaptcha.image.height", "55");
        properties.setProperty("kaptcha.session.key", "verifyCode");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        properties.setProperty("kaptcha.textproducer.font.size", "35");
        properties.setProperty("kaptcha.textproducer.char.length", "5");
     //   properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
