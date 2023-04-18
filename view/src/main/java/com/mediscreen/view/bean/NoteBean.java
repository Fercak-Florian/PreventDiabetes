package com.mediscreen.view.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteBean {
    private String id;
    private int patientId;
    private String dateOfCreation;
    private String content;
}
