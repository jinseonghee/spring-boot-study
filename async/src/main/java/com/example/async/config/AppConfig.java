package com.example.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AppConfig {

    @Bean("async-thread")
    public Executor asyncThread() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setCorePoolSize(10); //pool은 core size(10)가 다 차면 Queue에 들어가고 Queue가 다 차면(10) 다시 pool(20)~
        threadPoolTaskExecutor.setQueueCapacity(10);
        threadPoolTaskExecutor.setThreadNamePrefix("Async~");

        return threadPoolTaskExecutor;
    }
}
