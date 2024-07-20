package com.todoalura.todo_app.service;

import com.todoalura.todo_app.dto.TaskDto;
import com.todoalura.todo_app.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDto taskDto, Long userId);
    Task updateTask(Long taskId, TaskDto taskDto, Long userId);
    void deleteTask(Long taskId, Long userId);
    List<Task> getUserTasks(Long userId);
    Task getTaskById(Long taskId, Long userId);
}
