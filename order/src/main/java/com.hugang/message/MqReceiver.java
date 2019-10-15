package com.hugang.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收
 *
 * @author: hg
 * @date: 2019/9/24 08:59
 */
@Slf4j
@Component
public class MqReceiver {


    //1、直接定义监听的队列@RabbitListener(queues = "myQueue")
    //2、自动创建队列(springboot2.0以上可使用@RabbitListener(queueToDeclare = @Queue("myQueue"))
    // 来声明队列，此版本使用RabbitConfig来定义queue)
    //3、自动创建，Exchange和queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receive(String message) {

        log.info("message:{}", message);
    }

    /**
     * 数码供应商接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myExchange"),
            key = "computer",
            value = @Queue("computerQueue")
    ))
    public void processComputer(String message) {

        log.info("ComputerReceiver:{}", message);
    }
}
