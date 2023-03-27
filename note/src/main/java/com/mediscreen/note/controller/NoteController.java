package com.mediscreen.note.controller;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
            return ResponseEntity.internalServerError().build();
        } else {
            log.info("providing note list");
            return ResponseEntity.ok(notes);
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id) {
        try {
            Note note = noteService.getNote(id);
            log.info("providing note");
            return ResponseEntity.ok(note);
        } catch (Exception e) {
            log.warn("note not found for this id : " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/note")
    public ResponseEntity<Note> addNote(@Valid @RequestBody Note note, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("error in input fields");
            return ResponseEntity.badRequest().build();
        } else {
            Note addedNote = noteService.addNote(note);
            log.info("note created");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(addedNote.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note, @PathVariable String id, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("error in input field");
            return ResponseEntity.badRequest().build();
        } else {
            try {
                Note updatedNote = noteService.updateNote(id, note);
                log.info("note with id : " + id + " is updated");
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(updatedNote.getId())
                        .toUri();
                return ResponseEntity.created(location).build();
            } catch (Exception e) {
                log.warn("note not found for this id : " + id);
                return ResponseEntity.notFound().build();
            }
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable String id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("error during deleting notes with id : " + id);
            return ResponseEntity.notFound().build();
        }
    }
}

