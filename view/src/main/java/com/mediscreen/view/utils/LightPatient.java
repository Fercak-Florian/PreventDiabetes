package com.mediscreen.view.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LightPatient {
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    @NotBlank(message = "FirstName is mandatory")
    private String lastName;
}
