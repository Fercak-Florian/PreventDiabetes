package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "note", url = "${spring.cloud.openfeign.client.config.note.url}")
public interface MicroserviceNoteProxy {

    /*Signature des méthodes présentes dans NoteController*/

    @RequestMapping(value = "/note", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<NoteBean> getNotes();

    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    NoteBean getNote(@PathVariable("id") String id);

    @RequestMapping(value = "/note/patientId/{id}" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<NoteBean> getNotesByPatientId(@PathVariable("id") int id);

    @RequestMapping(value = "/note", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    NoteBean addNote(@RequestBody NoteBean noteBean);

    @RequestMapping(value = "/note/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    NoteBean updateNote(@RequestBody NoteBean noteBean, @PathVariable("id") String id);

    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    NoteBean deleteNote(@PathVariable("id") String id);
}
