package com.hugang.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @GetMapping("/login")
    public String login(){
        return "登录方法，跳转到登录页面";
    }

    @PostMapping("/add")
    public String add(){
        return "添加方法";
    }

    @PostMapping("/update")
    public String update(){
        return "更新方法";
    }


}
