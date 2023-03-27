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
        return noteRepository.findAll();
    }

    public Note getNote(String id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isEmpty()) {
            log.warn("note with id : " + id + " is not found");
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            log.info("note is found");
            return optionalNote.get();
        }
    }

    public Note addNote(Note note) {
        return noteRepository.insert(note);
    }

    public Note updateNote(String id, Note note){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isEmpty()){
            log.warn("note not found for this id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            Note noteToUpdate = optionalNote.get();
            noteToUpdate.setPatientId(note.getPatientId());
            noteToUpdate.setDateOfCreation(note.getDateOfCreation());
            noteToUpdate.setContent(note.getContent());
            noteRepository.save(noteToUpdate);
            return noteToUpdate;
        }
    }

    public void deleteNote(String id){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if(optionalNote.isEmpty()){
            log.warn("note not found for this id : " + id);
            throw new NoteNotFoundException("note with id : " + id + " is not found");
        } else {
            noteRepository.deleteById(id);
            log.info("note with the id " + id + " id deleted");
        }
    }
}
