package com.todoalura.todo_app.repository;

import com.todoalura.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository  extends JpaRepository <User, Long> {
    Optional<User>findByUserName(String userName);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
