package com.example.validation.validator;

import com.example.validation.annotation.YearMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearMonthValidator implements ConstraintValidator<YearMonth, String> {

    private String pattern;

    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern(); //초기화 시킬때 pattern을 가져옴으로써 pattern이 정상적으로 들어갔는지 확인
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) { //여기서 value는 getReqYearMonth
        //yyyyMM

        try {
            LocalDate localDate = LocalDate.parse(value + "01", DateTimeFormatter.ofPattern(this.pattern));
             //localdate이기 때문에 날짜까지 들어와야 하는데 안들어오기 때문에 01를 임의로 붙여서 자리 맞춤
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

