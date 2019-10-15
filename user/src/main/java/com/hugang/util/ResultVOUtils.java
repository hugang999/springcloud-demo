package com.hugang.util;


import com.hugang.VO.ResultVO;
import com.hugang.enums.ResultEnum;

public class ResultVOUtils {

    public static ResultVO success(Object object){
        return new ResultVO(0, "成功", object);
    }

    public static ResultVO success(){
        return new ResultVO(0, "成功", null);
    }

    public static ResultVO error(ResultEnum resultEnum){
        return new ResultVO(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

}
