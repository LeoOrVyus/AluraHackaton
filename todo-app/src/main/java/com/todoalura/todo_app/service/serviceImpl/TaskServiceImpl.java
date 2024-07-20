package com.todoalura.todo_app.service.serviceImpl;

import com.todoalura.todo_app.dto.TaskDto;
import com.todoalura.todo_app.entity.Task;
import com.todoalura.todo_app.entity.User;
import com.todoalura.todo_app.exception.TaskNotFoundException;
import com.todoalura.todo_app.exception.UnauthorizedAccessException;
import com.todoalura.todo_app.exception.UserNotFoundException;
import com.todoalura.todo_app.repository.TaskRepository;
import com.todoalura.todo_app.repository.UserRepository;
import com.todoalura.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task createTask(TaskDto taskDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCompleted(false);
        task.setUser(user);

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskDto taskDto, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You don't have permission to update this task");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCompleted(taskDto.isCompleted());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You don't have permission to delete this task");
        }

        taskRepository.delete(task);
    }

    @Override
    public List<Task> getUserTasks(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You don't have permission to access this task");
        }

        return task;
    }
}
