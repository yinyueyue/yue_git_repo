package com.pax.sms.core.exception;

import com.alibaba.fastjson.JSONObject;
import com.pax.sms.core.utils.ScopeUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.pax.sms.core.exception
 *
 * @author yyyty
 * @time :  2018/7/23
 * @description:
 */
@Component
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
        //是否为ajax请求
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code","FAIL");
            jsonObject.put("msg","系统异常");
            jsonObject.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
            ScopeUtils.response(response,jsonObject.toJSONString());
        }
        return null;
    }
}