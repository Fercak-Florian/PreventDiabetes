package com.mediscreen.report.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeCalculate {

    public int calculate(String dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.parse(dateOfBirth);
        return Period.between(date, currentDate).getYears();
    }
}
