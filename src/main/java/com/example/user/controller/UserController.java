package com.example.user.controller;

import com.example.user.exception.DuplicateUserException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.service.UserService;
import lombok.extern.slf4j.Slf4j; //log
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        log.info("Get all users request received");
        return new ResponseEntity<>(userService.allUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        log.info("Get user by id request received for id: {}", id);
        User user = userService.userById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Create user request received for user: {}", user);
        if(userService.userById(user.getUserId()).isPresent()){
            throw new DuplicateUserException("User already exists with id: " + user.getUserId());
        }
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
        log.info("Update user request received for id: {} and user: {}", id, user);
        if(!userService.userById(id).isPresent()){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        user.setUserId(id);
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        log.info("Delete user request received for id: {}", id);
        if(!userService.userById(id).isPresent()){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}