package com.hugang.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.hugang.beans.OrderDetail;
import com.hugang.beans.OrderMaster;
import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductInfo;
import com.hugang.common.product.clients.ProductClient;
import com.hugang.dto.OrderDto;
import com.hugang.enums.OrderStatusEnum;
import com.hugang.enums.PayStatusEnum;
import com.hugang.enums.ResultEnum;
import com.hugang.exception.OrderException;
import com.hugang.mapper.OrderDetailMapper;
import com.hugang.mapper.OrderMasterMapper;
import com.hugang.message.ProductReceiver;
import com.hugang.service.OrderService;
import com.hugang.utils.JedisUtils;
import com.hugang.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    /**
     * TODO 2.查询商品信息（调用商品服务）
     * TODO 3.计算总价
     * TODO 4.扣库存（调用商品服务）
     * 5.订单入库
     */
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        //一个订单生成一个订单id，orderMaster和orderDetail的关系为一对多，即一个订单能包含多个商品，同一个订单的
        //所有商品的orderId一样，但是每个商品的id不同，需要逐个生成
        Long orderId = KeyUtil.getUniqueKey();
        //2.查询商品信息（调用商品服务）
        List<Long> productIdList = orderDto.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
        if (null == productInfoList){
            throw new OrderException(ResultEnum.PRODUCTINFO_ERROR);
        }
        //3.计算总价(单价*数量)
        Double orderAmount = 0.0;
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()){
            for (ProductInfo productInfo : productInfoList){
                if (orderDetail.getProductId().equals(productInfo.getId())){
                    orderAmount += productInfo.getPrice() * orderDetail.getProductQuantity();

                    //将订单商品信息拷贝到orderDetail
                    orderDetail.setProductIcon(productInfo.getIcon());
                    orderDetail.setProductName(productInfo.getName());
                    orderDetail.setProductPrice(productInfo.getPrice());
                    //同一订单下所有商品的orderId一样
                    orderDetail.setOrderId(orderId);
                    //每个商品有单独的detailId
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());

                    //订单商品详情入库
                    orderDetailMapper.insert(orderDetail);
                }
            }
        }

        //4.扣库存（调用商品服务）（待修改：将扣库存方法中的判断库存是否足够的逻辑写到下面，直接从redis中获取，并删除
        //  product服务中的相关逻辑代码。
        //  product服务中判断库存是否足够的逻辑代码不能删，因为如果是第一次扣商品的库存，redis中没有相关商品的库存信息）
        List<DecreaseStock> decreaseStockList = orderDto.getOrderDetailList().stream()
                .map(e -> new DecreaseStock(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        for (DecreaseStock decreaseStock : decreaseStockList){
            String productStock = jedisUtils.get(
                    String.format(ProductReceiver.PRODUCT_STOCK_TEMPLATE,decreaseStock.getProductId()));
            if (null != productStock) {
                Integer stock = Integer.parseInt(productStock);
                if (decreaseStock.getProductQuantity() > stock){
                    throw new OrderException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
                }
            }
        }
        productClient.decreaseStock(decreaseStockList);

        //5.订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterMapper.insert(orderMaster);
        return orderDto;
    }

    @Override
    public OrderDto finish(Long orderId){
        //1、先查询订单(Optional:java8新特性，主要用来解决空指针异常，学习点)
        Optional<OrderMaster> orderMasterOptional = Optional.ofNullable(orderMasterMapper.selectOne(new OrderMaster().setOrderId(orderId)));
        if (!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2、判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3、完结订单
        OrderMaster orderMasterEntity = new OrderMaster();
        orderMasterEntity.setOrderId(orderMaster.getOrderId());
        orderMasterEntity.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterMapper.updateById(orderMasterEntity);
        //重新查询订单详情
        orderMaster = orderMasterMapper.selectById(orderId);

        //构造orderDto，包含orderMaster和List<OrderDetail>
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(
                new EntityWrapper<OrderDetail>().eq("order_id", orderMaster.getOrderId()));

        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }
}
