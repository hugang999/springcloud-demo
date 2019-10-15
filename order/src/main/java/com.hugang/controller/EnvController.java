package com.hugang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hg
 * @date: 2019/9/23 16:59
 */
@RestController
@RequestMapping(value = "/enva")
@RefreshScope
public class EnvController {

    @Value("${env}")
    private String env;

    @GetMapping(value = "/print")
    public String print(){
        return env;
    }
}
