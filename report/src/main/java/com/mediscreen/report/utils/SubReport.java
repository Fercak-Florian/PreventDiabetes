package com.mediscreen.report.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubReport {
    private String sex;
    private int numberOfTriggers;
    private int age;
}
