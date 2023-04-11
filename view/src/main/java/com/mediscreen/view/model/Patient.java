package com.mediscreen.view.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Patient {

    private String id;
    @NotBlank(message = "FirstName is mandatory")
    private String lastName;
    @NotBlank(message = "LastName is mandatory")
    private String firstName;
    @NotBlank(message = "Date of birth is mandatory")
    private String dob;
    @NotBlank(message = "Sex is mandatory")
    private String sex;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "Phone is mandatory")
    private String phone;
}
