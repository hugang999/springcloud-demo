package com.hugang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: hg
 * @date: 2019/9/25$
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    BUYER(1, "买家"),
    SELLER(2, "卖家");


    private Integer code;

    private String message;
}
