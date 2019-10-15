package com.hugang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品结果枚举类
 * @author hg
 * @date 2019/9/19 19:12
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /**
     * 商品不存在
     */
    PRODUCT_NOT_EXIST(1, "商品不存在"),
    /**
     * 商品库存不够
     */
    PRODUCT_STOCK_NOT_ENOUGH(2, "库存不够");

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 结果信息
     */
    private String message;


}
