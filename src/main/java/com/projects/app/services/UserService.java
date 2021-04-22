package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.user.User;
import com.projects.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) throws BackendError {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepo.save(user);
            user.setPassword(null);
        } catch (DataIntegrityViolationException ex) {
            String message = "Username " + user.getUsername() + " have been used";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return user;
    }
}
