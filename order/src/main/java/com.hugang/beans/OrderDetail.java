package com.hugang.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 订单包含的商品实体类，由于商品的信息是实时变化的，因此该实体类
 * 也需要有商品的相关信息，用以作为一个商品快照
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetail {

    @TableId
    private Long detailId;

    private Long orderId;

    private Long productId;

    private String productName;

    /**
     * 商品当前价格
     */
    private Double productPrice;

    /**
     * 商品购买数量
     */
    private Integer productQuantity;

    /**
     * 商品图标
     */
    private String productIcon;

    private Timestamp createTime;

    private Timestamp updateTime;


}
