package com.example.async.controller;


import com.example.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    //@Autowired 기본으로 생성자를 만들어 주겠다는 뜻
    private final AsyncService asyncService;


    public ApiController(AsyncService asyncService) { //Lombok을 사용하면 @RequiredArgumentConstructor를 붙여주면 된다.
        this.asyncService = asyncService;
    }

    @GetMapping("/hello")
    public CompletableFuture hello() {
        //asyncService.hello(); //asyncService의 hello method 호출
        //log.info("method end"); //AsyncService에 @Async 를 달아주지 않았다면 hello AsyncService의 hello method를
        //다 탈때까지 기다렸다가 response가 나가서 "hello"가 출력 될텐데, @Async가 붙었기 떄문에 "hello"라는 response를 받고 바로 나간다.
        log.info("Completable Future Init");
        return asyncService.run();
    }
}

