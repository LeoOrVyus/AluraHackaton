package com.todoalura.todo_app.service.serviceImpl;

import com.todoalura.todo_app.entity.Note;
import com.todoalura.todo_app.repository.NoteRepository;
import com.todoalura.todo_app.service.NoteService;
import com.todoalura.todo_app.dto.NoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Override
    public Note createNote(NoteDto noteDto, Long userId) {
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setDateTime(noteDto.getDateTime());
        note.setContent(noteDto.getContent());
        note.setUser(userService.findById(userId));
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long noteId, NoteDto noteDto, Long userId) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("Not authorized to update this note");
        }
        note.setTitle(noteDto.getTitle());
        note.setDateTime(noteDto.getDateTime());
        note.setContent(noteDto.getContent());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("Not authorized to delete this note");
        }
        noteRepository.deleteById(noteId);
    }

    @Override
    public List<Note> getUserNotes(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Override
    public Note getNoteById(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new NoteNotFoundException("Note not found"));
        if (!note.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("Not authorized to access this note");
        }
        return note;
    }
}
