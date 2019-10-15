package com.hugang.dto;

import com.hugang.beans.OrderDetail;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
/**
 * 数据传输对象层：用于将前端传过来的数据进行再次封装（前端传过来的数据已经被OrderForm封装过一次），一般是多个bean的部分或者
 * 所有属性的结合，前端需要的数据都在一个DTO中，这样，每次调用服务层的时候，只需要调用一次就可以完成所有的业务逻辑操作，而不是
 * 原来的直接调用业务逻辑层那样的，需要调用多次，对于分布式场景下，减少服务调用的次数，尤其重要。
 * @author hg
 */
public class OrderDto {
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

    private List<OrderDetail> orderDetailList;
}
