package com.hugang.controller;

import com.hugang.common.product.clients.ProductClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: hg
 * @date: 2019/9/27 10:20
 */
@RestController
//结合hystrix配置默认fallback方法
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    @Autowired
    private ProductClient productClient;

    //也可写进配置项
    //如果不写fallbackMethod属性,则默认使用defaultFallback
    //@HystrixCommand//(fallbackMethod = "fallback")
    //设置超时时间(默认为1秒)
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })

    //服务熔断
    @HystrixCommand(commandProperties = {
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  				//设置熔断
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),	//请求数达到10个后才计算错误率
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗，半开试探休眠时间，熔断器打开10秒之后，会放行
                                                                                                 // 部分请求，当错误率低于60%时，则认为服务正常，此时熔断器关闭
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),	//错误率达>60%则开启断路器，此时服务熔断
	})
    //@HystrixCommand
    @GetMapping(value = "/getProductList")
    public String getProductList(Integer number){
        if (number % 2 == 0){
            return "success";
        }
        //throw new RuntimeException("发生异常");
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject("http://localhost:8001/product/list", String.class);

        return response;
    }

    public String fallback(){
        return "太拥挤了，请稍后再试";
    }

    public String defaultFallback(){
        return "默认提示： 太拥挤了，请稍后再试";
    }
}
