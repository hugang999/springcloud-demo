package com.hugang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author: hg
 * @date: 2019/9/25 09:27
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("zuul")
    @RefreshScope
    @Primary
    public ZuulProperties zuulProperties(){
        return new ZuulProperties();
    }
}
