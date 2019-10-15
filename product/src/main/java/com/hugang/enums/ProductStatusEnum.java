package com.hugang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * 商品上下架状态
 * @author hg
 */
public enum ProductStatusEnum {
    /**
     * 在架
     */
    UP(0, "在架"),
    /**
     * 下架
     */
    DOWN(1, "下架");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;


}
