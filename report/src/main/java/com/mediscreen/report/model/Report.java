package com.mediscreen.report.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private String lastName;
    private String firstName;
    private int age;
    private String riskLevel;
}
