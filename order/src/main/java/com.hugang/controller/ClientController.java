package com.hugang.controller;

import com.hugang.common.product.beans.DecreaseStock;
import com.hugang.common.product.beans.ProductInfo;
import com.hugang.common.product.clients.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    RestTemplate restTemplate;

    @GetMapping(value = "/getProductMsg")
    public String getProductMessage() {
        //一、RestTemplate三种方法
        //RestTemplate restTemplate = new RestTemplate();
        //1.RestTemplate第一种方式(直接用restTemplate，url写死)
//        String response = restTemplate.getForObject("http://localhost:8001/msg", String.class);

        //2.RestTemplate第二种方式（利用loadBalancerClient通过应用名获取url,在使用restTemplate）
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PROVIDER-8001");
//        log.info("service.port={}", serviceInstance.getPort());
//        String url = String.format("http://%s:%s/msg", serviceInstance.getHost(), serviceInstance.getPort());
//        String response = restTemplate.getForObject(url, String.class);

        //3.RestTemplate第三种方式（利用@LoadBalancer,restTemplate使用服务名）
//        String response = restTemplate.getForObject("http://PROVIDER-8001/msg", String.class);
//        log.info("response={}", response);

        //二、Feign
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @GetMapping(value = "/getList")
    public Object getList(){
        List<ProductInfo> list = productClient.listForOrder(Arrays.asList(100000001L, 100000002L));
        log.info("list={}", list);
        return list;
    }

    @GetMapping(value = "/productDecreaseStock")
    public void decreaseStock(){
        DecreaseStock decreaseStock1 = new DecreaseStock(100000001L, 35);
        DecreaseStock decreaseStock2 = new DecreaseStock(100000002L, 35);
        productClient.decreaseStock(Arrays.asList(decreaseStock1, decreaseStock2));
    }
}
