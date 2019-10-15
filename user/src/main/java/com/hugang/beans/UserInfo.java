package com.hugang.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * @author: hg
 * @date: 2019/9/25 16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfo {

    @TableId
    private Long id;

    private String username;

    private String password;

    private String openId;

    /**
     * 用户角色，1买家2卖家
     */
    private Integer role;

    private Date createTime;

    private Date updateTime;
}
