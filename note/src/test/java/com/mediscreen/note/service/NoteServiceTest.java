package com.mediscreen.note.service;

import com.mediscreen.note.model.Note;
import com.mediscreen.note.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        noteService = new NoteService(noteRepository);
    }

    @Test
    @DisplayName("Test de la récupération de toutes les notes")
    public void testGetNotes(){
        /*ARRANGE*/
        Note note = new Note("1", "2023-30-03", "content test");
        List<Note> notes= new ArrayList<>();
        notes.add(note);
        when(noteRepository.findAll()).thenReturn(notes);

        /*ACT*/
        List<Note> result = noteService.getNotes();

        /*ASSERT*/
        assertThat(result.get(0).getContent()).isEqualTo("content test");
        verify(noteRepository).findAll();
    }

    @Test
    @DisplayName("Test de la récupération d'une note")
    public void testGetNote(){
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        Optional<Note> optionalNote = Optional.of(note);
        when(noteRepository.findById(id)).thenReturn(optionalNote);

        /*ACT*/
        Note result = noteService.getNote(id);

        /*ASSERT*/
        assertThat(result.getContent()).isEqualTo("content test");
        verify(noteRepository).findById(id);
    }

    @Test
    @DisplayName("Echec de la récupération d'un note")
    public void testGetNoteReturnNull(){
        /*ARRANGE*/
        String id = "1";
        Optional optionalNote = Optional.empty();
        when(noteRepository.findById(id)).thenReturn(optionalNote);

        /*ACT*/
        Note result = noteService.getNote(id);

        /*ASSERT*/
        assertThat(result).isNull();
        verify(noteRepository).findById(id);
    }

    @Test
    @DisplayName("Test de la sauvegarde d'une note")
    public void testAddNote(){
        /*ARRANGE*/
        Note addedNote = new Note("1", "2023-30-03", "content test");
        when(noteRepository.insert(addedNote)).thenReturn(addedNote);

        /*ACT*/
        Note result = noteService.addNote(addedNote);

        /*ASSERT*/
        assertThat(result.getContent()).isEqualTo("content test");
        verify(noteRepository).insert(addedNote);
    }

    @Test
    @DisplayName("Test de la mise à jour d'une note")
    public void testUpdateNote(){
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        Optional<Note> optionalNote = Optional.of(note);
        Note updatedNote = new Note("1", "2023-30-03", "content test updated");
        when(noteRepository.findById(id)).thenReturn(optionalNote);
        when(noteRepository.save(note)).thenReturn(updatedNote);

        /*ACT*/
        Note result = noteService.updateNote(id, note);

        /*ASSERT*/
        assertThat(result.getContent()).isEqualTo("content test updated");
        verify(noteRepository).findById(id);
        verify(noteRepository).save(note);
    }

    @Test
    @DisplayName("Echec de la mise à jour d'une note")
    public void testUpdateNoteReturnNull(){
        /*ARRANGE*/
        String id = "1";
        Optional<Note> optionalNote = Optional.empty();
        Note note = new Note("1", "2023-30-03", "content test");
        when(noteRepository.findById(id)).thenReturn(optionalNote);

        /*ACT*/
        Note result = noteService.updateNote(id,note);

        /*ASSERT*/
        assertThat(result).isNull();
        verify(noteRepository).findById(id);
    }

    @Test
    @DisplayName("Test de la suppression d'une note")
    public void testDeleteNote(){
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        Optional<Note> optionalNote = Optional.of(note);
        when(noteRepository.findById(id)).thenReturn(optionalNote);

        /*ACT*/
        Note result = noteService.deleteNote(id);

        /*ASSERT*/
        assertThat(result.getContent()).isEqualTo("content test");
        verify(noteRepository).deleteById(id);
    }

    @Test
    @DisplayName("Echec de la suppression d'une note")
    public void testDeleteNoteReturnNull(){
        /*ARRANGE*/
        String id = "1";
        Optional<Note> optionalNote = Optional.empty();
        when(noteRepository.findById(id)).thenReturn(optionalNote);

        /*ACT*/
        Note result = noteService.deleteNote(id);

        /*ASSERT*/
        assertThat(result).isNull();
        verify(noteRepository).findById(id);
    }
}
