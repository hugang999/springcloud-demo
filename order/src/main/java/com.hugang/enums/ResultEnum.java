package com.hugang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果枚举类
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    CART_EMPTY(2, "购物车为空"),
    ORDER_NOT_EXIST(3, "订单不存在"),
    ORDER_STATUS_ERROR(4, "订单状态错误"),
    ORDER_DETAIL_NOT_EXIST(5, "订单详情不存在"),
    PRODUCTINFO_ERROR(6, "获取商品列表失败"),
    PRODUCT_STOCK_NOT_ENOUGH(7, "商品库存不足");

    private Integer code;

    private String message;

}
