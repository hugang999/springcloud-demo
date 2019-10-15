package com.hugang.filters;

import com.hugang.common.constant.CookieConstant;
import com.hugang.common.constant.RedisConstant;
import com.hugang.util.CookieUtil;
import com.hugang.util.JedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * pre权限校验过滤器，区分买家和卖家
 * @author: hg
 * @date: 2019/9/25 14:14
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JedisUtils jedisUtils;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    //过滤器优先级，越小优先级越高
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    /**
     * 返回true表示调用run方法
     */
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        /**
         * /order/create:买家
         * /order/finish:买家
         * /product/list:都可访问
         */

        //买家的标识为request中有名为openId的cookie，且有值
        if ("/order/order/create".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, CookieConstant.OPEN_ID);
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            }
        }

        //卖家的标识为request中有名为token的cookie，且有值，并且redis中有该cookie值的信息
        if ("/order/order/finish".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())
                    || StringUtils.isEmpty(jedisUtils.get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            }
        }
        return null;
    }
}
