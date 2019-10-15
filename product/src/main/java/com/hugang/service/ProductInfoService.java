package com.hugang.service;

import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductInfo;

import java.util.List;

public interface ProductInfoService {


    /**
     * 查询所有在架商品
     * @return List<ProductInfo> 商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 根据商品id列表查询商品列表
     * @param productIdList 商品列表id
     * @return List<ProductInfo> 商品列表
     */
    List<ProductInfo> findList(List<Integer> productIdList);

    /**
     * 根据商品id更新商品库存
     * @param decreaseStockList
     */
    void decreaseStock(List<DecreaseStock> decreaseStockList);
}
