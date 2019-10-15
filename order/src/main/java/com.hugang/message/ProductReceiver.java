package com.hugang.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hugang.common.product.beans.ProductInfo;
import com.hugang.common.utils.JsonUtil;
import com.hugang.utils.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: hg
 * @date: 2019/9/24 15:44
 */
@Component
@Slf4j
public class ProductReceiver {

    @Autowired
    private JedisUtils jedisUtils;

    public final static String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    /**
     * 商品库存变动之后发送消息给订单服务，订单服务将该信息存储到redis中，之后下订单服务可以直接从redis中获取
     * 该商品的库存信息，判断库存是否足够
     * @param message
     */
    @RabbitListener(queues = "productInfo")
    public void process(String message){
        List<ProductInfo> productInfoList = (List<ProductInfo>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfo>>() {});
        log.info("从队列productInfo接收到消息：{}", productInfoList);

        //存储到redis
        for (ProductInfo productInfo : productInfoList){
            jedisUtils.set(String.format(PRODUCT_STOCK_TEMPLATE, productInfo.getId()),
                    String.valueOf(productInfo.getStock()));
        }
    }


}
