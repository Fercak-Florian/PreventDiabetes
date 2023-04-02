package com.mediscreen.note.controller;

import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note")
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> notes = noteService.getNotes();
        if (notes.isEmpty()) {
            log.warn("note list is empty");
            throw new NoteNotFoundException("note list is empty");
        } else {
            log.info("providing note list");
            return ResponseEntity.ok(notes);
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id) {
        Note note = noteService.getNote(id);
        if (note == null) {
            log.warn("note not found for this id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            log.info("providing note");
            return ResponseEntity.ok(note);
        }
    }

    @GetMapping("/note/patientId/{id}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable String id){
       List<Note> notes =  noteService.getNotesByPatientId(id);
       if(notes.isEmpty()){
           log.warn("patient note list is empty");
           throw new NoteNotFoundException("patient note list is empty");
       } else {
           log.info("providing patient note list");
           return ResponseEntity.ok(notes);
       }
    }

    @PostMapping("/note")
    public ResponseEntity<Note> addNote(@Valid @RequestBody Note note) {
        Note addedNote = noteService.addNote(note);
        log.info("note created");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(addedNote);
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note, @PathVariable String id) {
        Note updatedNote = noteService.updateNote(id, note);
        if (updatedNote == null) {
            log.warn("note not found for this id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            log.info("note with id : " + id + " is updated");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updatedNote.getId())
                    .toUri();
            return ResponseEntity.created(location).body(updatedNote);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable String id) {
        Note note = noteService.deleteNote(id);
        if (note == null) {
            log.warn("error during deleting notes with id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            log.info("note with the id " + id + " is deleted");
            return ResponseEntity.ok().body(note);
        }
    }
}

