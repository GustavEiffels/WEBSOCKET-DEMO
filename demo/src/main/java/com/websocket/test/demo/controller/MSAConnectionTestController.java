package com.websocket.test.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MSAConnectionTestController {

    private Environment env;

    @Autowired
    public MSAConnectionTestController(Environment env){
        this.env = env;
    }

    @GetMapping("/group-chat/test")
    public String msaTestAPI(HttpServletRequest request){
        return "GROUP_CHATTING_MSA_TEST_COMPLETE PORT : "+request.getServerPort()+ " INSTANCE : "+env.getProperty("local.server.port");
    }
}
