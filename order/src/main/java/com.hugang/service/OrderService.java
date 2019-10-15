package com.hugang.service;


import com.hugang.dto.OrderDto;

public interface OrderService {


    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    OrderDto finish(Long orderId);
}
