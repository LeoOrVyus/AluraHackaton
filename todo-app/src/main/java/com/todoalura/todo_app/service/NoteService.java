package com.todoalura.todo_app.service;

import com.todoalura.todo_app.entity.Note;
import com.todoalura.todo_app.dto.NoteDto;

import java.util.List;

public interface NoteService {
    Note createNote(NoteDto noteDto, Long userId);
    Note updateNote(Long noteId, NoteDto noteDto, Long userId);
    void deleteNote(Long noteId, Long userId);
    List<Note> getUserNotes(Long userId);
    Note getNoteById(Long noteId, Long userId);
}
