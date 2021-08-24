package com.example.validation.dto;

import com.example.validation.annotation.YearMonth;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {

    @NotBlank //이름에 blank가 들어가지 않게 해야 함.
    private String name;

    @Max(value = 90, message = "나이 초과") //나이 최대값 90 을 넘을 시 error. 모든 validation에는 message를 넣을 수 있음.
    private int age;

    @Email // validation annotation에 email 사용 (이러한 양식에 맞지 않으면 error)
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxxx-xxxx") //핸드폰에 대한 정규식
    private String phoneNumber;

    //@Size(min = 6, max = 6) //6자리만 들어와야 함
    @YearMonth(pattern = "yyyyMMdd")
    private String reqYearMonth; //yyyyMM

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReqYearMonth() {
        return reqYearMonth;
    }

    public void setReqYearMonth(String reqYearMonth) {
        this.reqYearMonth = reqYearMonth;
    }

    /*
        @AssertTrue(message = "yyyyMM의 형식에 맞지 않습니다.")
        public boolean isReqYearMonthValidation() {

            try {
                LocalDate localDate = LocalDate.parse(getReqYearMonth() + "01", DateTimeFormatter.ofPattern("yyyyMMdd")); //LocalData는 format에 day까지 들어가기
                                                                                                      //때문에 pattern에 dd도 넣어주고, getReqYearMonth에 01을 붙여 형식 만듬
            } catch (Exception e) {
                return false;
            }
            return true;  //parsing이 잘 되면 true, 아니면 false
        }
    */

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", reqYearMonth='" + reqYearMonth + '\'' +
                '}';
    }
}
