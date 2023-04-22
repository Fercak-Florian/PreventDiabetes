package com.mediscreen.view.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

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
