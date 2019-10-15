package com.hugang.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hugang.beans.OrderDetail;
import com.hugang.dto.OrderDto;
import com.hugang.enums.ResultEnum;
import com.hugang.exception.OrderException;
import com.hugang.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
/**
 * orderForm转换成orderDto的转换类
 */
public class OrderFormToOrderDtoConverter {

    public static OrderDto convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> orderDetailList;

        //将orderForm的item（购物车里的商品）转换成List<OrderDetail>
        try {
            orderDetailList = gson.fromJson(orderForm.getItem(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            log.error("【json转换】错误，string={}", orderForm.getItem());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }

        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }
}
