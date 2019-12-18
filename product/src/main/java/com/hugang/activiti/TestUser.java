package com.hugang.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: hg
 * @date: 2019/12/18 10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

}
