package com.pax.sms.core.utils;


import com.pax.sms.common.model.UserLoginInfo;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ScopeUtils {

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;
    /**
     * SpringMvc下获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;

    }

    public static String getAccessToken(){
        return getRequest().getHeader("access_token");
    }

    /**
     * SpringMvc下获取response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        return response;

    }




    public static HttpSession getSession() {
        try {
            HttpSession session = getRequest().getSession(false);
            if (session == null) {
                session = getRequest().getSession(true);

            }
            return session;
        } catch (Exception e) {
            return null;
        }
    }
	
	/*public static String getUserName() {
		HttpSession session = getRequest().getSession(false);
		if (session == null) {
			return "";
		} else {
			User user = (User) session.getAttribute("user");
			return user.getUserName();
		}
	}*/
	
/*	public static User getUser() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		return (User) session.getAttribute("user");
	}*/

    public static Long getUserId(){
        String token  =getAccessToken();
        return JwtTokenUtils.getAppUID(token);
    }

    public static void response(HttpServletResponse response,String str) {
        response.setHeader("cache-control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }


}
