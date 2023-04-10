package com.mediscreen.report.utils;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculate {

    public int calculate(String dateOfBirth){
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.parse(dateOfBirth);
        return Period.between(date, currentDate).getYears();
    }
}
