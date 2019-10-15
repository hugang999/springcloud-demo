package com.hugang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果枚举类
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {
    LOGIN_FAILED(1, "登录失败"),
    ROLE_ERROR(2, "角色权限错误");

    private Integer code;

    private String message;

}
