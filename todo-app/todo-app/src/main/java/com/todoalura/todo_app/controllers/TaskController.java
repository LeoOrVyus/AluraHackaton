package com.todoalura.todo_app.controllers;

import com.todoalura.todo_app.service.TaskService;
import com.todoalura.todo_app.service.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto, Authentication auth) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        Task task = taskService.createTask(taskDto, userId);
        return ResponseEntity.ok(convertToDto(task));
    }

    @PutMapping("/{taskId}")
}
