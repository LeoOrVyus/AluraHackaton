package com.todoalura.todo_app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDto {
    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private String content;
}
