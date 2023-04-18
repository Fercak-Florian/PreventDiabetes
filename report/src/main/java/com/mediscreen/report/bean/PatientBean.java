package com.mediscreen.report.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {

    private int id;

    private String lastName;

    private String firstName;

    private String dob;

    private String sex;

    private String address;

    private String phone;
}
