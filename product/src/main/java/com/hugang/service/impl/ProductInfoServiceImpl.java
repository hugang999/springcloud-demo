package com.hugang.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductInfo;
import com.hugang.common.utils.JsonUtil;
import com.hugang.enums.ProductStatusEnum;
import com.hugang.enums.ResultEnum;
import com.hugang.exception.ProductException;
import com.hugang.mapper.ProductInfoMapper;
import com.hugang.service.ProductInfoService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.selectList(new EntityWrapper<ProductInfo>().eq("status", ProductStatusEnum.UP.getCode()));
    }

    @Override
    public List<ProductInfo> findList(List<Integer> productIdList) {
        //region Description
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //endregion
        return productInfoMapper.selectList(new EntityWrapper<ProductInfo>().in("id", productIdList));
    }


    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStock> decreaseStockList) {

        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStock decreaseStock: decreaseStockList){
            //先查询该商品的库存信息
            ProductInfo productInfo = productInfoMapper.selectById(decreaseStock.getProductId());
            //如果该id对应的商品id不存在，抛出异常
            if (null == productInfo){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算商品库存与购买数量的差值
            Integer num = productInfo.getStock() - decreaseStock.getProductQuantity();
            //如果商品库存不够，抛出异常;
            if (num < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
            }
            //库存够则更新减库存之后的值
            productInfo.setStock(num);
            productInfoMapper.updateById(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

    @Override
    public void decreaseStock(List<DecreaseStock> decreaseStockList){
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockList);
        //发送库存消息给队列
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoList));
    }
}
