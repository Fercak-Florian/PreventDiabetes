package com.mediscreen.note.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
