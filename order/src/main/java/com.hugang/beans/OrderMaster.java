package com.hugang.beans;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
/**
 * 订单实体类
 */
public class OrderMaster {

    @TableId
    private Long orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private Double orderAmount;

    /**
     * 订单状态，默认为0，表示新下单
     */
    private Integer orderStatus;

    /**
     * 支付状态，默认为0，表示未支付
     */
    private Integer payStatus;

    private Timestamp createTime;

    private Timestamp updateTime;
}
