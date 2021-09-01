package com.example.interceptor.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController { //아무 권한이 없는 사용자 모두가 들어와 사용 가능한 controller(openApi)

    @GetMapping("/hello")
    public String hello() {
        return "public hello";
    }
}
