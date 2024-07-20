package com.todoalura.todo_app.controllers;

import com.todoalura.todo_app.dto.NoteDto;
import com.todoalura.todo_app.entity.Note;
import com.todoalura.todo_app.service.NoteService;
import com.todoalura.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto, @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromAuthHeader(authHeader);
        Note note = noteService.createNote(noteDto, userId);
        return ResponseEntity.ok(convertToDto(note));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long noteId, @RequestBody NoteDto noteDto, @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromAuthHeader(authHeader);
        Note note = noteService.updateNote(noteId, noteDto, userId);
        return ResponseEntity.ok(convertToDto(note));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId, @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromAuthHeader(authHeader);
        noteService.deleteNote(noteId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getUserNotes(@RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromAuthHeader(authHeader);
        List<Note> notes = noteService.getUserNotes(userId);
        return ResponseEntity.ok(notes.stream().map(this::convertToDto).toList());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long noteId, @RequestHeader("Authorization") String authHeader) {
        Long userId = extractUserIdFromAuthHeader(authHeader);
        Note note = noteService.getNoteById(noteId, userId);
        return ResponseEntity.ok(convertToDto(note));
    }

    private Long extractUserIdFromAuthHeader(String authHeader) {
        // Implementa la lógica para extraer el ID del usuario desde el encabezado de autorización
        return 1L; // Ejemplo, reemplázalo con la lógica real
    }

    private NoteDto convertToDto(Note note) {
        NoteDto dto = new NoteDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDateTime(note.getDateTime());
        dto.setContent(note.getContent());
        return dto;
    }
}
