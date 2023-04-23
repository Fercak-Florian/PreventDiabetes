package com.mediscreen.report.proxy;

import com.mediscreen.report.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "note", url = "${spring.cloud.openfeign.client.config.note.url}")
public interface MicroserviceNoteProxy {

    @RequestMapping(value = "/note/patientId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<NoteBean> getNotesByPatientId(@PathVariable("id") int id);
}
