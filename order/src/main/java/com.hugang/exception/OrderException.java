package com.hugang.exception;

import com.hugang.enums.ResultEnum;

/**
 * 订单服务异常
 */
public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
