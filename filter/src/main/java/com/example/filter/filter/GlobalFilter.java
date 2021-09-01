package com.example.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
//@Component //Filter를 등록하기 위(Spring에 의해서 bean으로 관리)
@WebFilter(urlPatterns = "/api/user/*")//원하는 request Url 패턴 넣어줌(특정 클래스, 컨트롤러에만 filter를 적용하고 싶을 경(배열로 여러가지 패턴 가능))
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //전처리
        //HttpServletRequest httpServletRequest = (HttpServletRequest)request; //형변환
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest)request); //캐싱이 되어 있어서 body 내용을 계속 읽을 수 있음
        //HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse)response); //형변환을 시켜서 매개변수로 넘겨줌

        //cachContent가 길이만 지정하지 내용은 지정하지 않기 떄문에 error

        /*
        BufferedReader br = httpServletRequest.getReader(); //body를 보기위해

        br.lines().forEach(line -> {
            log.info("url : {}, line : {} ", url, line);
        });
        */
        chain.doFilter(httpServletRequest, httpServletResponse); //doFilter 이후에 내용을 찍어야 한다.

        String url = httpServletRequest.getRequestURI();

        //후처리(후처리에서 모든 정보를 기록)
        //req

        String reqContent  = new String(httpServletRequest.getContentAsByteArray());

        log.info("request url : {}, request body : {}" ,url, reqContent);

        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        httpServletResponse.copyBodyToResponse(); //위에 getContentAsByteArray()를 해주어 내용을 다 빼버렸기 때문에
                                                  //console에는 뜨지만, httpbody에는 뜨지 않아서, 한번 더 내용을 복사해서 넣어줌

        log.info("response status : {} , responseBody : {}", httpStatus, resContent);

    }
}
