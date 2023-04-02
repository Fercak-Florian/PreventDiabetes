package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "note", url = "localhost:8082")
public interface MicroserviceNoteProxy {

    /*Signature des méthodes présentes dans NoteController*/

    @GetMapping("/note")
    List<NoteBean> getNotes();

    @GetMapping("/note/{id}")
    NoteBean getNote(@PathVariable String id);

    @PostMapping("/note")
    NoteBean addNote(@RequestBody NoteBean noteBean);

    @PutMapping("/note/{id}")
    NoteBean updateNote(@RequestBody NoteBean noteBean, @PathVariable String id);

    @DeleteMapping("/note/{id}")
    NoteBean deleteNote(@PathVariable String id);
}
