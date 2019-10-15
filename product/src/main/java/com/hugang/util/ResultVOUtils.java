package com.hugang.util;


import com.hugang.VO.ResultVO;

public class ResultVOUtils {

    public static ResultVO success(Object object){
        return new ResultVO(0, "成功", object);
    }
}
