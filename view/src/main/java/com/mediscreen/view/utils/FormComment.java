package com.mediscreen.view.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FormComment {

    private String message = "";
    private boolean error;

    public boolean clearMessage() {
        this.message = "";
        return true;
    }
}
