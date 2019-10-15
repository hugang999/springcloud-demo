package com.hugang.common.constant;

/**
 * @author: hg
 * @date: 2019/9/25 16:55
 */
public interface CookieConstant {

    /**
     * 卖家登录之后设置的cookie的名称
     */
    String TOKEN = "token";

    /**
     * 买家登录之后设置的cookie名称
     */
    String OPEN_ID = "openId";

    /**
     * 过期时间，单位为秒
     */
    Integer EXPIRE = 7200;
}
