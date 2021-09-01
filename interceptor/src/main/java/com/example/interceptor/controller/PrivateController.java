package com.example.interceptor.controller;

import com.example.interceptor.annotation.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
@Auth //Interceptor에서 다음 method나 controller에 이 annotation이 있으면 session을 검사해서 있을 때만 통과시킴
@Slf4j
public class PrivateController { //내부사용자, 세션이 인증된 사용자만 허용

    @GetMapping("/hello")
    public String hello() {
        log.info("private hello controller");
        return "private hello";
    }
}
