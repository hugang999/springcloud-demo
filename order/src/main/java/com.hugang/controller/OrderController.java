package com.hugang.controller;

import com.hugang.VO.ResultVO;
import com.hugang.converter.OrderFormToOrderDtoConverter;
import com.hugang.dto.OrderDto;
import com.hugang.enums.ResultEnum;
import com.hugang.exception.OrderException;
import com.hugang.form.OrderForm;
import com.hugang.service.OrderService;
import com.hugang.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单：
     * 1.参数校验
     * 2.查询商品信息（调用商品服务）
     * 3.计算总价
     * 4.扣库存（调用商品服务）
     * 5.订单入库
     */
    @PostMapping(value = "/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //对象转化：OrderForm -> OrderDto
        OrderDto orderDto = OrderFormToOrderDtoConverter.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDto result = orderService.create(orderDto);
        Map<String, Long> map = new HashMap<>();

        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);

    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public ResultVO finish(Long orderId){
        return ResultVOUtil.success(orderService.finish(orderId));
    }
}
