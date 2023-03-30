package com.mediscreen.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.exception.NoteNotFoundException;
import com.mediscreen.note.model.Note;
import com.mediscreen.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private NoteController noteController;

    @MockBean
    private NoteService noteService;

    @BeforeEach
    public void init() {
        noteController = new NoteController(noteService);
    }

    @Test
    @DisplayName("Test de récupération de toutes les notes")
    public void testGetNotes() throws Exception {
        /*ARRANGE*/
        Note note = new Note("1", "2023-30-03", "content test");
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        when(noteService.getNotes()).thenReturn(notes);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/note"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].content", is("content test")));
        verify(noteService).getNotes();
    }

    @Test
    @DisplayName("Echec de la récupération de la liste des notes")
    public void testGetNotesThrowsNoteNotFoundException() throws Exception {
        /*ARRANGE*/
        List<Note> notes = new ArrayList<>();
        when(noteService.getNotes()).thenReturn(notes);
        /*ACT AND ASSERT*/

        mockMvc.perform(get("/note")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof NoteNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("note list is empty"));
        verify(noteService).getNotes();
    }

    @Test
    @DisplayName("Test de récupération d'une note")
    public void testGetNote() throws Exception {
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        when(noteService.getNote(id)).thenReturn(note);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/note/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content test")));
        verify(noteService).getNote(id);
    }

    @Test
    @DisplayName("Echec de récupération d'une note")
    public void testGetNoteThrowsNoteNotFoundException() throws Exception {
        /*ARRANGE*/
        String id = "1";
        when(noteService.getNote(id)).thenReturn(null);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/note/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof NoteNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("note with id : " + id + " is not found"));
        verify(noteService).getNote(id);
    }

    @Test
    @DisplayName("Test de la sauvegarde d'une note")
    public void testAddNote() throws Exception{
        /*ARRANGE*/
        Note addedNote = new Note("1", "2023-30-03", "content test");
        ObjectMapper objectMapper = new ObjectMapper();
        when(noteService.addNote(addedNote)).thenReturn(addedNote);

        /*ACT AND ASSERT*/
        mockMvc.perform(post("/note")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addedNote)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("content test")));
        verify(noteService).addNote(addedNote);
    }

    @Test
    @DisplayName("Test de la mise à jour d'une note")
    public void testUpdateNote() throws Exception{
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        Note updatedNote = new Note("1", "2023-30-03", "content test updated");
        ObjectMapper objectMapper = new ObjectMapper();
        when(noteService.updateNote(id, note)).thenReturn(updatedNote);

        /*ACT AND ASSERT*/
        mockMvc.perform(put("/note/{id}", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("content test updated")));
        verify(noteService).updateNote(id, note);
    }

    @Test
    @DisplayName("Echec de la mise à jour d'une note")
    public void testUpdateNoteThrowsNoteNotFoundException() throws Exception{
        /*ARRANGE*/
        String id = "1";
        Note note = new Note("1", "2023-30-03", "content test");
        ObjectMapper objectMapper = new ObjectMapper();
        when(noteService.updateNote(id, note)).thenReturn(null);

        /*ACT AND ASSERT*/
        mockMvc.perform(put("/note/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof NoteNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("note with id : " + id + " is not found"));
        verify(noteService).updateNote(id, note);
    }

    @Test
    @DisplayName("Test de la suppression d'une note")
    public void testDeleteNote() throws Exception{
        /*ARRANGE*/
        String id ="1";
        Note note = new Note("1", "2023-30-03", "content test");
        when(noteService.deleteNote(id)).thenReturn(note);

        /*ACT AND ASSERT*/
        mockMvc.perform(delete("/note/{id}", id))
                .andExpect(content().string(containsString("content test")))
                .andExpect(status().isOk());
        verify(noteService).deleteNote(id);
    }

    @Test
    @DisplayName("Echec de la suppression d'une note")
    public void testDeleteNoteThrowsNoteNotFoundException()throws Exception{
        /*ARRANGE*/
        String id ="1";
        when(noteService.deleteNote(id)).thenReturn(null);

        /*ACT AND ASSERT*/
        mockMvc.perform(delete("/note/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof NoteNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("note with id : " + id + " is not found"));
        verify(noteService).deleteNote(id);
    }
}
