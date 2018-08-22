package com.pax.sms.core.utils;

import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * com.pax.sms.core.utils
 *
 * @author yyyty
 * @time :  2018/6/25
 * @description:
 */
public class EmailHelper extends Authenticator {

    private final static Logger logger = LoggerFactory.getLogger(EmailHelper.class);

    public static String encapsulateText(List values, String emailTemplate) {
        String template = FileUtils.readAsString("templates/" + emailTemplate).toString();

        String regex = "\\{\\{}}";
        for (Object value : values) {
            String text = java.util.regex.Matcher.quoteReplacement(String.valueOf(value));
            template = template.replaceFirst(regex, String.valueOf(text));
        }

        return template;
    }

    /**
     * 发送带HTML等复杂内容的邮件
     *
     * @param to
     * @param subject
     * @param htmlContent
     */
    public static void sendMimeMessageMail(String to, String subject, String htmlContent,String nickName) {
        try {

            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            //设置自定义发件人昵称
            String nick="";
            try {
                nick=javax.mail.internet.MimeUtility.encodeText(nickName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            helper.setFrom(new InternetAddress(nick+" <"+ConfigConstant.emailAddress+">"));
         //   helper.setFrom(ConfigConstant.emailAddress);
            helper.setTo(to);

            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setSentDate(new Date());

            sender.setHost(ConfigConstant.emailHost);
            sender.setUsername(ConfigConstant.emailUserName);
            sender.setPassword(ConfigConstant.emailPassword);
            logger.info(ConfigConstant.emailHost + "[发信:" + ConfigConstant.emailAddress + "][收信:" + to + "]");


            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);  //  将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.port", ConfigConstant.emailPort);
            prop.put("mail.smtp.timeout", "25000");
            sender.setJavaMailProperties(prop);
            sender.send(message);
        } catch (Exception e) {
//            logger.error(logger_prefix_email + "发送简单邮件时发生异常！", e);
            throw new BusinessException(e.getMessage());
        }
    }
}
