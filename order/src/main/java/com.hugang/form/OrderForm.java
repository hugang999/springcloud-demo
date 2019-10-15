package com.hugang.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用于封装前台传过来的数据，并校验
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "用户微信openid不能为空")
    private String openId;

    @NotEmpty(message = "购物车(items)不能为空")
    private String item;
}
