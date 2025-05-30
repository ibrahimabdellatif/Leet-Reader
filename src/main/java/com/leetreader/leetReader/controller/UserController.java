package com.leetreader.leetReader.controller;

import com.leetreader.leetReader.dto.UserDTO;
import com.leetreader.leetReader.dto.UserPasswordDTO;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/@{username}")
    public Optional<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/adduser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/@{username}/change-password")
    public ResponseEntity<String> updateUserPassword(@PathVariable String username, @Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        String result = userService.updateUserPassword(username, userPasswordDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
