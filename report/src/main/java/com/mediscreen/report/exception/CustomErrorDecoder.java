package com.mediscreen.report.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder{

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String invoqueur, Response response) {

        /*suppression du début de la requete http*/
        String url = response.request().toString();
        int index = url.indexOf(":");
        url = url.substring(index + 1);
        index = url.indexOf(":");
        url = url.substring(index +1);
        index = url.indexOf("/");
        url = url.substring(index);

        if (response.status() == 404 && url.startsWith("/patient")) {
            return new PatientNotFoundException("patient does not exist");
        } else if (response.status() == 404 && url.startsWith("/note")) {
            return new NoteNotFoundException("list is empty");
        }
        {
            return defaultErrorDecoder.decode(invoqueur, response);
        }
    }
}
