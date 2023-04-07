package com.mediscreen.view.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientBean {

    private String id;

    private String firstName;

    private String lastName;

    private String family;

    private String given;

    private String dob;

    private String sex;

    private String address;

    private String phone;
}
