package com.mediscreen.view.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response response) {

        if (response.status() == 404 && response.request().toString().contains("http://localhost:8081/patient")) {
            return new PatientNotFoundException("patient does not exist");
        } else if (response.status() == 404 && response.request().toString().contains("http://localhost:8082/note")) {
            return new NoteNotFoundException("list is empty");
        }
        {
            return defaultErrorDecoder.decode(invoqueur, response);
        }
    }
}
