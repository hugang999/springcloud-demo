package com.hugang.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * http请求返回的最外层对象
 */
public class ResultVO<T> {

    /**
     * 错误码，正常为0
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;
}
