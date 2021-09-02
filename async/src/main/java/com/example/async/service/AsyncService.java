package com.example.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncService {

    @Async("async-thread") //괄호 안에 Thread의 Bean 이름 넣어줌
    public CompletableFuture run() { //CompletableFuture는 다른 Thread에서 실행을 시키고 결과를 반환받는 형태
        return new AsyncResult(hello()).completable();
    }


    //@Async //비동기로 동작 할 수 있도록 달아줌
    public String hello() {
        /*
         @Asnyc, @EnableAsnyc Annotation을 사용하지 않으면 Thread 인스턴스 해서 사용
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        */

        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                log.info("Thread sleep .... ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "async hello";
    }
}
