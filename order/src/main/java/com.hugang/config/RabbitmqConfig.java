package com.hugang.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hg
 * @date: 2019/9/24 10:07
 */
@Configuration
public class RabbitmqConfig {

//    //@Bean
//    public Queue getQueue(){
//        return new Queue("myQueue", true);
//    }

    @Bean
    public Queue ProductInfoQueue(){
        return new Queue("productInfo", true);
    }
}
