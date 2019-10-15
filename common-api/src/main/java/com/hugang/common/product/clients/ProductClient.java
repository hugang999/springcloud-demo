package com.hugang.common.product.clients;

import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

/**
 * product服务的feign远程调用接口客户端
 * @author: hg
 * @date: 2019/9/21 11:03
 */
@FeignClient(name = "PRODUCT", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping(value = "/msg")
    String productMsg();

    /**
     * 获取商品列表（供订单服务调用）
     * 注：@RequestBody封装的是请求体的数据，前端使用post请求，Content-Type为application/json
     *    feign消费服务时，如果参数前面什么都没写，则默认使用@RequestBody
     *    使用get请求时，可以使用@Pathvariable或者@RequestParam来获取参数
     * @param productIdList 商品id列表
     * @return 商品列表
     */
    @PostMapping(value = "/product/listForOrder")
    List<ProductInfo> listForOrder(List<Long> productIdList);

    /**
     * 根据商品id更新商品库存
     * @param cartDtoList cart信息
     */
    @PostMapping(value = "/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStock> cartDtoList);

    @Component
    class ProductClientFallback implements ProductClient{
        @Override
        public String productMsg() {
            return null;
        }

        @Override
        public List<ProductInfo> listForOrder(List<Long> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStock> cartDtoList) {
            throw new RuntimeException("扣库存失败");
        }
    }
}
