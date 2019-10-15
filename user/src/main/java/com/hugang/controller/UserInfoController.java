package com.hugang.controller;

import com.hugang.Service.UserInfoService;
import com.hugang.VO.ResultVO;
import com.hugang.beans.UserInfo;
import com.hugang.common.constant.CookieConstant;
import com.hugang.common.constant.RedisConstant;
import com.hugang.enums.ResultEnum;
import com.hugang.enums.RoleEnum;
import com.hugang.util.CookieUtil;
import com.hugang.util.JedisUtils;
import com.hugang.util.ResultVOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author: hg
 * @date: 2019/9/25 16:25
 */
@RestController
@RequestMapping(value = "/login")
public class UserInfoController {

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "/buyer")
    public ResultVO buyer( String openId, HttpServletResponse response){
        //1、openid和数据库中的信息是否匹配,如果不匹配，则返回登录失败
        UserInfo userInfo = userInfoService.findByOpenId(openId);
        if (null == userInfo){
            return ResultVOUtils.error(ResultEnum.LOGIN_FAILED);
        }
        //2、判断角色,如果角色权限不是买家，则返回权限错误
        if (!RoleEnum.BUYER.getCode().equals(userInfo.getRole())){
            return ResultVOUtils.error(ResultEnum.ROLE_ERROR);
        }
        //3、cookie设置openId=abc
        CookieUtil.set(response, CookieConstant.OPEN_ID, openId, CookieConstant.EXPIRE);


        return ResultVOUtils.success();
    }

    @GetMapping(value = "/seller")
    public ResultVO seller(String openId, HttpServletResponse response, HttpServletRequest request){
        //判断是否登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (null != cookie &&
                !StringUtils.isEmpty(jedisUtils.get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))){
            return ResultVOUtils.success();
        }

        //1、openid和数据库中的信息是否匹配,如果不匹配，则返回登录失败
        UserInfo userInfo = userInfoService.findByOpenId(openId);
        if (null == userInfo){
            return ResultVOUtils.error(ResultEnum.LOGIN_FAILED);
        }
        //2、判断角色,如果角色权限不是买家，则返回权限错误
        if (!RoleEnum.SELLER.getCode().equals(userInfo.getRole())){
            return ResultVOUtils.error(ResultEnum.ROLE_ERROR);
        }

        //3、在redis中设置：key=UUID，value=openId

        String token = UUID.randomUUID().toString().replace("-", "");

        jedisUtils.setStringWithExipre(String.format(RedisConstant.TOKEN_TEMPLATE,token), openId, CookieConstant.EXPIRE);

        //4、cookie设置token=UUID
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVOUtils.success();
    }
}
