package com.hugang.message;

import com.hugang.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author: hg
 * @date: 2019/9/24 11:09
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message){
//        log.info("StreamReceiver:{}", message);
//    }

    /**
     * 接收orderDto对象
     * @param message
     */
    @StreamListener(StreamClient.INPUT)
    //接收成功之后发送一条消息给INPUT2
    @SendTo(StreamClient.INPUT2)
    public String process(OrderDto message){
        log.info("StreamReceiver:{}", message);
        return "receive success";
    }

    /**
     * 接收INPUT2消息
     * @param message
     */
    @StreamListener(StreamClient.INPUT2)
    public void process2(String message){
        log.info("StreamReceiver2:{}", message);
    }
}
