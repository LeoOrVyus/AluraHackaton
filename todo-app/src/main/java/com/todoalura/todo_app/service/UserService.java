package com.todoalura.todo_app.service;

import com.todoalura.todo_app.dto.UserDto;
import com.todoalura.todo_app.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User registerUser(UserDto userDto);
    User findByUsername(String username);
    User updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    void updateProfilePicture(Long userId, MultipartFile file) throws IOException;
}
