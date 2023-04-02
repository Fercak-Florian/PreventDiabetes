package com.mediscreen.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Note {
    @Id
    private String id;
    @NotBlank(message = "Patient id is mandatory")
    private String patientId;
    @NotBlank(message = "Date of creation is mandatory")
    private String dateOfCreation;
    @NotBlank(message = "Content is mandatory")
    private String content;
}
