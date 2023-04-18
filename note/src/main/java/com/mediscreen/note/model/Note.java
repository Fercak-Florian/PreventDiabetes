package com.mediscreen.note.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "note")
public class Note {

    public Note(int patientId, String dateOfCreation, String content){
        this.patientId = patientId;
        this.dateOfCreation = dateOfCreation;
        this.content = content;
    }

    @Id
    private String id;
    @DecimalMin(message = "must be greater than 0", value = "0", inclusive = false)
    private int patientId;
    @NotBlank(message = "Date of creation is mandatory")
    private String dateOfCreation;
    @NotBlank(message = "Content is mandatory")
    private String content;
}
