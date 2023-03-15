package com.mediscreen.preventdiabetes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.module.FindException;

@AllArgsConstructor
@Data
@Document(collection = "patient")
public class Patient {

    @Id
//    @Indexed(unique = true)
    private int id;
    private String firstName;
    private String lastName;
    private String family;
    private String given;
    private String dob;
    private String sex;
    private String address;
    private String phone;
}
