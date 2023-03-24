package com.mediscreen.note.controller;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping("/note")
    public Optional<Note> getNote(String id) {
        return noteService.getNote(id);
    }

    @PostMapping("/patHistory/add")
    public void addNote(@RequestBody Note note) {
        noteService.addNote(note);
    }
}
