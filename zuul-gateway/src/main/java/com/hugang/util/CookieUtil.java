package com.hugang.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 * @author: hg
 * @date: 2019/9/25 16:51
 */
public class CookieUtil {

    public static void set(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : request.getCookies()){
                if (cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }
        return null;
    }
}
