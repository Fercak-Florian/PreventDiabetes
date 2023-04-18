package com.mediscreen.patient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "last_name")
    @NotBlank(message = "LastName is mandatory")
    private String lastName;

    @Column(name = "first_name")
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @Column(name = "dob")
    @NotBlank(message = "Date of birth is mandatory")
    private String dob;

    @Column(name = "sex")
    @NotBlank(message = "Sex is mandatory")
    private String sex;

    @Column(name = "address")
    @NotBlank(message = "Address is mandatory")
    private String address;

    @Column(name = "phone")
    @NotBlank(message = "Phone is mandatory")
    private String phone;
}
