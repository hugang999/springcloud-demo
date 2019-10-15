package com.hugang.controller;

import com.hugang.dto.OrderDto;
import com.hugang.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: hg
 * @date: 2019/9/24 11:06
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

//    @GetMapping("/sendMessage")
//    public void sendMessage(){
//        String message = "now:" + new Date();
//        streamClient.output().send(MessageBuilder.withPayload(message).build());
//    }

    /**
     * 发送orderDto对象
     */
    @GetMapping("/sendMessage")
    public void sendMessage(){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1111111111111111L);
        streamClient.output().send(MessageBuilder.withPayload(orderDto).build());
    }
}
