package com.mediscreen.note.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Data
@Document(collection = "note")
public class Note {

    public Note(String patientId, String dateOfCreation, String content){
        this.patientId = patientId;
        this.dateOfCreation = dateOfCreation;
        this.content = content;
    }

    @Id
    private String id;
    @NotBlank(message = "Patient id is mandatory")
    private String patientId;
    @NotBlank(message = "Date of creation is mandatory")
    private String dateOfCreation;
    @NotBlank(message = "Content is mandatory")
    private String content;
}
