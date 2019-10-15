//package com.hugang.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
//import org.springframework.stereotype.Component;
//
///**
// * 实现动态路由配置更新,也可写在ZuulApplication中
// * @author: hg
// * @date: 2019/9/25 10:21
// */
//@Component
//public class ZuulConfig {
//
//    @ConfigurationProperties(prefix = "zuul")
//    @RefreshScope
//    public ZuulProperties zuulProperties(){
//        return new ZuulProperties();
//    }
//}
