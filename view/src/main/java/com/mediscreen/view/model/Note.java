package com.mediscreen.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Note {
    @Id
    private String id;
    @DecimalMin(message = "must be greater than 0", value = "0", inclusive = false)
    private int patientId;
    @NotBlank(message = "Date of creation is mandatory")
    private String dateOfCreation;
    @NotBlank(message = "Content is mandatory")
    private String content;
}
