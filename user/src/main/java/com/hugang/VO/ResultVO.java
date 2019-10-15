package com.hugang.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: hg
 * @date: 2019/9/25 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

}
