package com.example.filter.dto;


import lombok.*; //compile 할 때 만 Lombok 사용(자바 컴파일러가 컴파일을 할 때, getter,setter 메서드가 만들어 지는 형식)

//@Getter
//@Setter
@Data //hashCode, ToString 까지 모두 만들어 줌
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //전체 생성자
public class User {

    private String name;
    private int age;
}
