package com.mediscreen.view.model;

/*import org.springframework.data.annotation.Id;*/

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Patient {
    /*@Id*/
    private String id;
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    @NotBlank(message = "LastName is mandatory")
    private String lastName;
    @NotBlank(message = "Family is mandatory")
    private String family;
    @NotBlank(message = "Given is mandatory")
    private String given;
    @NotBlank(message = "Date of birth is mandatory")
    private String dob;
    @NotBlank(message = "Sex is mandatory")
    private String sex;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "Phone is mandatory")
    private String phone;
}