package com.mediscreen.view.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response response) {

        if (response.status() == 404 && response.request().toString().contains("http://localhost:8081/patient")) {
            System.out.println("affichage de reason : " + response.reason());
            System.out.println("body de la reponse : " + response.body().toString());
            System.out.println("request de la reponse : " + response.request());
            return new PatientNotFoundException("patient does not exist");
        } else if (response.status() == 404 && response.request().toString().contains("http://localhost:8082/note")) {
            System.out.println("affichage de reason : " + response.reason());
            System.out.println("body de la reponse : " + response.body().toString());
            System.out.println("request de la reponse : " + response.request());
            return new NoteNotFoundException("list is empty");
        }
        {
            return defaultErrorDecoder.decode(invoqueur, response);
        }
    }
}
