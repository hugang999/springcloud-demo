package com.hugang.common.product.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
/**
 * 产品信息实体类
 * @author: hg
 */
@TableName(value = "product_info")
public class ProductInfo {

    @TableId
    private Long id;

    private String name;

    private Double price;

    private Integer stock;

    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态，0正常，1下架
     */
    private Integer status;

    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
