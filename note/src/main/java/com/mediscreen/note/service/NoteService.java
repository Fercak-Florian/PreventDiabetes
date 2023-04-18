package com.mediscreen.note.service;

import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotes() {
        log.info("providing note list");
        return noteRepository.findAll();
    }

    public Note getNote(String id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isEmpty()) {
            log.warn("note with id : " + id + " is not found");
            return null;
        } else {
            log.info("note is found");
            return optionalNote.get();
        }
    }

    public List<Note> getNotesByPatientId(int id){
        log.info("providing patient note list");
        return noteRepository.findByPatientId(id);
    }

    public Note addNote(Note note) {
        return noteRepository.insert(note);
    }

    public Note updateNote(String id, Note note){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isEmpty()){
            log.warn("note not found for this id : " + id);
            return null;
        } else {
            Note noteToUpdate = optionalNote.get();
            noteToUpdate.setPatientId(note.getPatientId());
            noteToUpdate.setDateOfCreation(note.getDateOfCreation());
            noteToUpdate.setContent(note.getContent());
            return noteRepository.save(noteToUpdate);
        }
    }

    public Note deleteNote(String id){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isEmpty()){
            log.warn("note not found for this id : " + id);
            return null;
        } else {
            noteRepository.deleteById(id);
            log.info("note with the id " + id + " id deleted");
            return optionalNote.get();
        }
    }
}
