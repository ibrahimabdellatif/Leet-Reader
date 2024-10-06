package com.leetreader.leetReader.controller;

import com.leetreader.leetReader.dto.UserDTO;
import com.leetreader.leetReader.dto.UserPasswordDTO;
import com.leetreader.leetReader.mapper.UserDTOMapper;
import com.leetreader.leetReader.model.User;
import com.leetreader.leetReader.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
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

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/@{username}/change-password")
    public ResponseEntity<String> updateUserPassword(@PathVariable String username , @RequestBody UserPasswordDTO userPasswordDTO) {
//        return new ResponseEntity<>(userService.updateUser(username, user), HttpStatus.OK);
        String result;
        try {
            result =  userService.updateUserPassword(username,userPasswordDTO);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    for test
    @GetMapping("/demo")
    public String demo(){
        var u = SecurityContextHolder.getContext().getAuthentication();

        if(u == null || !u.isAuthenticated()){
            System.out.println("User is not authenticated");
            return "user is not authenticated";
        }
        u.getAuthorities().forEach(System.out::println);
        System.out.println(u.getAuthorities().isEmpty());
        return "demo";
    }
}
