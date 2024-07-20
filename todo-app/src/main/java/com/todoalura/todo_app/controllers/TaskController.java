package com.todoalura.todo_app.controllers;

import com.todoalura.todo_app.dto.TaskDto;
import com.todoalura.todo_app.entity.Task;
import com.todoalura.todo_app.service.TaskService;
import com.todoalura.todo_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<TaskDto>updateTask(@Valid @RequestBody Long taskId, TaskDto taskDto, Authentication auth){
        Long userId = userService.findByUsername(auth.getName()).getId();
        Task updatedTask = taskService.updateTask(taskId, taskDto, userId);
        return ResponseEntity.ok(convertToDto(updatedTask));

   }

   @DeleteMapping("/{taskId}")
    public ResponseEntity<Void>deleteTask (@PathVariable Long taskId,Authentication auth ){
        Long userId = userService.findByUsername(auth.getName()).getId();
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
   }

   @GetMapping
    public ResponseEntity<List<TaskDto>>getUserTasks(Authentication auth){
        Long userId = userService.findByUsername(auth.getName()).getId();
        List<Task> tasks = taskService.getUserTasks(userId);
        List<TaskDto> taskDtos = tasks.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
   }

   @GetMapping("{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId, Authentication auth) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        Task task = taskService.getTaskById(taskId, userId);
        return ResponseEntity.ok(convertToDto(task));

   }

   private TaskDto convertToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
       dto.setDescription(task.getDescription());
       dto.setDueDate(task.getDueDate());
       dto.setCompleted(task.isCompleted());
       return dto;
   }

}
