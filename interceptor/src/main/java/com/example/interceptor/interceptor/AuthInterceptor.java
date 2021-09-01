package com.example.interceptor.interceptor;

import com.example.interceptor.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import com.example.interceptor.annotation.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@Component //spring에 의해서 관리 되어야 하기 떄문에 component 사용
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        //filter에서 처리 했던 것처럼 body 부분을 보려면, Interceptor에서도 ContentCachingRequestWrapper를 사용해 형변환을 시켜주어 넣어주면 된다.
        //아니면 filter에서 request를 넣어주기 때문에 filter단에서 형변환을 시켜서 사용할 수 있다.

        URI uri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString())
                .build()
                .toUri();

        log.info("request url : {}" , url);
        boolean hasAnnotation = checkAnnotation(handler, Auth.class);//@Auth 가지고 있는지 권한 check
        log.info("has annotation : {} ", hasAnnotation);

        // 나의 서버는 모두 public으로 동작 하는데
        // 단! Auth 권한을 가진 요청에 대해선 세션, 쿠키
        if(hasAnnotation) {
            //권한체크
            String query = uri.getQuery();
            if(query.equals("name=steve")) {
                return true;
            }

            //return false;
            throw new AuthException();
        }

        return true; //true면 interceptor를 넘어서 안의 logic을 타는데, false면 Interceptor에서 바로 return
    }

    private boolean checkAnnotation(Object handler, Class clazz) {

        //resource javascript, html (resource에 대한 요청일 때는 무조건 통과)
        if( handler instanceof ResourceHttpRequestHandler) { //handler 타입이 ResourceHttpRequestHandler 이거 이면
            return true; //무조건 통과
        }

        //annotation check
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if( null != handlerMethod.getMethodAnnotation(clazz) || null != handlerMethod.getBeanType().getAnnotation(clazz)) {

            //Auth annotation이 있을 때는 true
            return true;
        }
        return false; //위의 조건이 아닐땐 모두 실패
    }
}
