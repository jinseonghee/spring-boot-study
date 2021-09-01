package com.example.filter.controller;


import com.example.filter.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //lombok에서 지원하는 것으로 system.out으로 출력하는게 아니라 log변수 사용 가능
@RestController
@RequestMapping("/api/user")
public class ApiController {

    @PostMapping("")
    public User user(@RequestBody User user) {
        log.info("User : {} , {}", user, user); //info는 정보를 출력한다는 의미. {}는 문자열 뒤에 들어가는 객체와 매칭(user의 첫번째 객체에 들어가는
        //toString이 맨 처음{}, 두번째 .. 이런식으로 진행 == user.toString())
        return user;
    }
}
