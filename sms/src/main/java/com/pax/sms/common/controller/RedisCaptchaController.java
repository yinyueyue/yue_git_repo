package com.pax.sms.common.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * com.pax.sms.comon.controller
 *
 * @author yyyty
 * @time :  2018/6/12
 * @description: 生成图片验证码
 */
@Controller
public class RedisCaptchaController {

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @RequestMapping(value = "/captcha-image", method = RequestMethod.GET)
    public void getKaptchaImage(HttpServletRequest request,
                                HttpServletResponse response,String verifyCodeKey) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        paxRedisTemplate.setWithExpireTimeStr(ConfigConstant.CAPTCHA_TAG, verifyCodeKey, capText, 180);
        Cookie cookie = new Cookie("captchaCode", verifyCodeKey);
        cookie.setPath("/");
        cookie.setMaxAge(120);  //设置cookie有效时间为60s
        response.addCookie(cookie);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();

        } finally {
            out.close();

        }
    }


}
