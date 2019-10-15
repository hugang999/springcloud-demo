package com.hugang.utils;

import com.hugang.VO.ResultVO;

/**
 * 返回结果工具类
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        return new ResultVO(0, "成功", object);
    }
}
