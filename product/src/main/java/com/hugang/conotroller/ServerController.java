package com.hugang.conotroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @GetMapping(value = "/msg")
    public String msg(){
        return "this is product message-8003";
    }
}
