package com.example.validation.controller;

import com.example.validation.dto.User;
import com.example.validation.dto.User1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiController {

/*
    @PostMapping("/user")
    public ResponseEntity user(@RequestBody User user) {
        System.out.println(user);
        System.out.println(user);

        if(user.getPhoneNumber() == "xxx-xxxx-xxxx") { //핸드폰 번호가 저런 형태가 아니면 BadRequest로 처리
            //if(user.getAge() >= 90) //이런식으로 유효식이 계속 늘어나기 때문에 Spring validation 사용
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
        return ResponseEntity.ok(user);
    }

*/

    //validation 사용
    @PostMapping("/user")
    //public ResponseEntity user(@Valid @RequestBody User user) { //사용할 객체(User)에 대해서 validation이 필요할 떈, @valid annotaion 사용
                                                                //그렇게 되면 @valid가 붙은 객체에 들어가 validation에 대한 검증을 한 후 맞지 않으면 error 처리
     public ResponseEntity user(@Valid @RequestBody User user, BindingResult bindingResult) { // BindingResult를 사용하면 예외처리가 바로 터지는게 아니라 validation에 대한
                                                                                              //결과가 BindingResult로 값이 들어옴
                                                                                              // BindingResult 객체가 없다면 예외처리를 통해 같이 처리)

        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(ObjectError -> { //어떠한 Object가 error가 났는지 메시지 받음
                FieldError field = (FieldError) ObjectError; // 어떠한 field에서 error가 났는지 값을 가져옴
                String message = ObjectError.getDefaultMessage();

                System.out.println("field : " + field.getField());
                System.out.println(message);

                sb.append("field : " + field.getField());
                sb.append("message : " + message);
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString()); //validation에 맞지 않으면, BadRequest return
        }
        System.out.println(user);

        return ResponseEntity.ok(user);
    }
}
