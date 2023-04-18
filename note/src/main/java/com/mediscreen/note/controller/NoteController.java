package com.mediscreen.note.controller;

import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@Api("API executant les operation CRUD sur les notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ApiOperation("Endpoint permettant la récupération de toutes les notes")
    @RequestMapping(value = "/note", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ApiOperation("Endpoint permettant la récupération d'une note par son id")
    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> getNote(@PathVariable("id") String id) {
        Note note = noteService.getNote(id);
        if (note == null) {
            log.warn("note not found for this id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            log.info("providing note");
            return ResponseEntity.ok(note);
        }
    }

    @ApiOperation("Endpoint permettant la récupération des notes liées au patient")
    @RequestMapping(value = "/note/patientId/{id}" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable("id") int id){
       List<Note> notes =  noteService.getNotesByPatientId(id);
       if(notes.isEmpty()){
           log.warn("patient note list is empty");
           throw new NoteNotFoundException("patient note list is empty");
       } else {
           log.info("providing patient note list");
           return ResponseEntity.ok(notes);
       }
    }

    @ApiOperation("Endpoint permettant d'ajouter une note")
    @RequestMapping(value = "/note", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ApiOperation("Endpoint permettant de mettre à jour une note")
    @RequestMapping(value = "/note/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> updateNote(@Valid @RequestBody Note note, @PathVariable("id") String id) {
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

    @ApiOperation("Endpoint permettant la suppression d'une note")
    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> deleteNote(@PathVariable("id") String id) {
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

