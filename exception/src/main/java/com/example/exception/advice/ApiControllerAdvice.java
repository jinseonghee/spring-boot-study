package com.example.exception.advice;


import com.example.exception.controller.ApiController;
import com.example.exception.dto.Error;
import com.example.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice(basePackageClasses = ApiController.class) //속성값을 이렇게 지정해 주면 ApiController에서만 동작하는 advice가 됨.
public class ApiControllerAdvice { //전부다 client가 잘못한 error

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) { //MissingServletRequestParameterException error는 이 method를 탐

        System.out.println(e.getClass().getName()); // 어떤 class의 어떤 name인지 알려줌.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) { //객체(User)의 값이 없을 경우 발생

        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            FieldError field = (FieldError) error;

            String filedName = field.getField();
            String message = field.getDefaultMessage();
            String value = field.getRejectedValue().toString();

            /*
            System.out.println("--------------");
            System.out.println(filedName);
            System.out.println(message);
            System.out.println(value);
            */

            Error errorMessage = new Error();
            errorMessage.setField(filedName);
            errorMessage.setMessage(message);
            errorMessage.setInvalidValue(value);

            errorList.add(errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());//client가 어디로 요청했는데 Error 났는지 알 수 있음(매개변수로 httpServletRequest값을 가져옴)
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest) {//@validation을 할 경우 값이 올바르지 않을때
        //constraintViolationException은 어떠한 feild의 정보가 잘 못되었는지 정보를 가지고 있음

        List<Error> errorList = new ArrayList<>();

        e.getConstraintViolations().forEach(error -> {

            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false); //Stream 생성
            List<Path.Node> list = stream.collect(Collectors.toList()); //Stream을 List로 바꿈

            String field = list.get(list.size() -1).getName(); //list의 마지막의 getName 호출
            String message = error.getMessage();
            String invalidValue = error.getInvalidValue().toString();

            /*
            System.out.println("---------------");
            System.out.println(field);
            System.out.println(message);
            System.out.println(invalidValue);
            */

            Error errorMessage = new Error();
            errorMessage.setField(field);
            errorMessage.setMessage(message);
            errorMessage.setInvalidValue(invalidValue);

            errorList.add(errorMessage);

        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());//client가 어디로 요청했는데 Error 났는지 알 수 있음(매개변수로 httpServletRequest값을 가져옴)
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e,HttpServletRequest httpServletRequest) { //값이 null일 경우

        List<Error> errorList = new ArrayList<>();

        String fieldName = e.getParameterName();
        //String fileType = e.getParameterType();
        String invalidValue = e.getMessage();

        /*
        System.out.println(fieldName);
        System.out.println(fileType);
        System.out.println(invalidValue);
        */

        Error errorMessage = new Error();
        errorMessage.setField(fieldName);
        errorMessage.setMessage(e.getMessage());
        //errorMessage.setInvalidValue(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());//client가 어디로 요청했는데 Error 났는지 알 수 있음(매개변수로 httpServletRequest값을 가져옴)
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
