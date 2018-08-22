package com.pax.sms.core.filter;

import com.pax.sms.common.model.UserLoginInfo;
import com.pax.sms.constant.ConfigConstant;
import com.pax.sms.core.cache.PaxRedisTemplate;
import com.pax.sms.core.exception.BusinessException;
import com.pax.sms.core.utils.DateUtils;
import com.pax.sms.core.utils.FastJsonObject;
import com.pax.sms.core.utils.JwtTokenUtils;
import com.pax.sms.core.utils.ScopeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * com.pax.sms.filter
 *
 * @author yyyty
 * @time :  2018/6/12
 * @description:WEB Token 过滤器，判断token是否合法
 */
public class TokenFilter implements Filter {
    private static List<String> EXLUSIVE_URLS = new ArrayList<>();
    private static List<String> EXLUSIVE_STATIC_RESOURCE = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaxRedisTemplate paxRedisTemplate;

    @Value("${spring.redis.session.timeout}")
    private int EXPIRE_SECONDS;

    @Value("${server.servlet.context-path}")
    private String PROJECT_NAME;

    @Value("${spring.filter.exlude.url}")
    private String EXLUSIVE_STR_URLS;

    @Value("${spring.filter.exlude.static.resource}")
    private String EXLUSIVE_STATIC_RESOURCE_STR;

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("TokenFilter start ............................................");
        String[] urls = StringUtils.isBlank(EXLUSIVE_STR_URLS) ? new String[]{} : EXLUSIVE_STR_URLS.split(",");
        List<String> urlList = new ArrayList<>();
        for (String url : urls) {
            urlList.add(PROJECT_NAME + url);
        }
        //初始化排除的url
        EXLUSIVE_URLS.addAll(urlList);

        //初始化排除的静态资源
        String[] resources = StringUtils.isBlank(EXLUSIVE_STATIC_RESOURCE_STR) ? new String[]{} : EXLUSIVE_STATIC_RESOURCE_STR.split(",");
        List<String> resourceList = new ArrayList<>();
        for (String resource : resources) {
            resourceList.add(resource);
        }
        //初始化排除的url
        EXLUSIVE_STATIC_RESOURCE.addAll(resourceList);


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("cache-control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        //设置支持跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, access_token, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String requestUrl = request.getRequestURI();

        FastJsonObject result = new FastJsonObject();

        String method = request.getMethod();
        //跨域请求有一次预请求，第一次为预检请求，为OPTIONS
        if ("OPTIONS".equals(method)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //若在排除列表内，直接通过，不判断token
        String resourceType = requestUrl.contains(".") ? requestUrl.substring(requestUrl.lastIndexOf(".")) : null;
        logger.info("request url : [{}] ",requestUrl);
        if (EXLUSIVE_URLS.contains(requestUrl) || EXLUSIVE_URLS.contains("/" + requestUrl.split("/")[1] + "/*") || EXLUSIVE_STATIC_RESOURCE.contains(resourceType)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            String token = "null".equalsIgnoreCase(request.getHeader("access_token"))?"":request.getHeader("access_token");
            //token不为空，表示已登录
            if (StringUtils.isNotBlank(token)) {
                try {
                    Long userId = JwtTokenUtils.getAppUID(token);

                    UserLoginInfo userInfo = paxRedisTemplate.get(ConfigConstant.USER_TAG, String.valueOf(userId), new UserLoginInfo());
                    //token过期或者token不一致
                    if (null == userInfo || !token.equals(userInfo.getToken())) {
                        throw new BusinessException("Invalid Token");
                    }

                    //更新最后登录时间
                    userInfo.setLastLoginTime(DateUtils.getCurrentDateString());
                    paxRedisTemplate.setWithExpireTimeObj(ConfigConstant.USER_TAG, String.valueOf(userId), userInfo, EXPIRE_SECONDS);
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } catch (Exception e) {
                    logger.error("用户登录错误信息:[{}]", e.getMessage(), e);

                    result.put("status", ConfigConstant.PAGE_SESSION_TIMEOUT);
                    result.put("code", 302);
                    result.put("msg", "登录已超时，请重新登录");
                    ScopeUtils.response(response, result.toString());
                    return;
                }

            } else {
                result.put("status", ConfigConstant.RESOURCE_UNAVAILABLE);
                result.put("code", 302);
                result.put("msg", "资源不可用,请登录后再试");
                ScopeUtils.response(response, result.toString());
                return;
            }
        }
    }

    @Override
    public void destroy() {
        logger.info("TokenFilter destroy ................................");
    }

}
