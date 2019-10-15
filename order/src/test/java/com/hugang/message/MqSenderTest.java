package com.hugang.message;

import com.hugang.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MqSenderTest extends OrderApplicationTests {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }

    @Test
    public void sendComputerOrder() {
        amqpTemplate.convertAndSend("myExchange", "computer", "computerOrder，time:" + new Date());
    }
}