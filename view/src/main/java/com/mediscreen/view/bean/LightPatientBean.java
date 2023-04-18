package com.mediscreen.view.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LightPatientBean {

    public LightPatientBean(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;
    }

    private String lastName;
    private String firstName;
}
