package com.example.exception.controller;


import com.example.exception.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApiController {

    @GetMapping("")
    public User get(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age){
        //required는 @RequestParameter의 값이 없어도 동작을 하되, name은 Null.
        //required의 default 값은 true 이고 만약 required = true 일때, 주소뒤에 ?nmae=1234이러한 parameter 값이 들어있지 않으면 에러를 만듦

        User user = new User();
        user.setName(name);
        user.setAge(age);

        int a = 10 + age; //age의 값을 넣어주지 않아 Null point Exception을 만듬

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user){ //@valid를 붙여 예외 터지도록 함

        System.out.println(user);
        return user;
    }

    //GlibalController에 exception을 지정했더라도, 우선순위는 Controller에 지정한 Handler이다.
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("api controller");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}


