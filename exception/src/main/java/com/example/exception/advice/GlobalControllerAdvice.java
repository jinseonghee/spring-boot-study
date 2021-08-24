package com.example.exception.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.exception.controller")//RestAPI 기준이기 때문에 이 annotation을 사용
//(basePackages = "com.example.exception.controller") 하위에 있는 excepiton을 모두 잡는다는 뜻
//@ControllerAdvice //viewResolver를 사용할 경우
public class GlobalControllerAdvice { //전체적인 Exception을 다 잡음

    //error가 터지면 여기를 탐.
    @ExceptionHandler(value = Exception.class) //어떠한 값을 잡을건지(Exception 처리할지)를 설정.(이 경우는 전체적인 예외를 다 잡음)
    public ResponseEntity exception(Exception e) { //RestApi이기 때문에 ResponseEntity를 뱉음. 예외도 받을 수 있음(위에서 설정한 value의 예외 값을
                                                   //여기 매게변수 Excecption e로 받음 ).

        System.out.println(e.getClass().getName()); //어디에 error가 터졌는지 class의 name을 출력
        System.out.println("---------------------");
        System.out.println(e.getLocalizedMessage());
        System.out.println("---------------------");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(""); //server에서 일어나는 Error는 Internal server Error
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
