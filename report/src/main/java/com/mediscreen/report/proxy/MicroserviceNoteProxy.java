package com.mediscreen.report.proxy;

import com.mediscreen.report.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note", url = "localhost:8082")
public interface MicroserviceNoteProxy {

    @GetMapping("/note/patientId/{id}")
    List<NoteBean> getNotesByPatientId(@PathVariable String id);
}
