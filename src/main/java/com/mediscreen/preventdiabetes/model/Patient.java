package com.mediscreen.preventdiabetes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.module.FindException;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "patient")
public class Patient {

    @Id
    private String id;
    private String firstName;
    //@Indexed(unique = true)
    private String lastName;
    private String family;
    private String given;
    private String dob;
    private String sex;
    private String address;
    private String phone;
}
