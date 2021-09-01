package com.example.interceptor.config;

import com.example.interceptor.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor // final로 선언된 객체들을 생성자에서 주입받도록 해준다.
public class MvcConfig implements WebMvcConfigurer {

    @Autowired //spring에서 AuthInterceptor를 Autowired로 자기자신을 넣을 수 있지만, 순환참조가 일어나기 때문에 @RequiredArgsConstructor 사용
               //bean error 나서 @Autowired 사용
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/private/*"); //Interceptor에 authInterceptor 등록
                                                    //addPathPatterns을 하면 검사하고 싶은 패턴에 대해서만 추가 가능
    }
}
