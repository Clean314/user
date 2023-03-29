package com.example.user.controller;

import com.example.user.model.User;
import com.example.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/v1/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping()
//    public ResponseEntity<List<User>> getAllUser(){
//        return new ResponseEntity<>(userService.allUser(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id){
//        return new ResponseEntity<>(userService.userById(id), HttpStatus.OK);
//    }
//
//    @PostMapping()
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
//        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUserById(@PathVariable String id){
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}

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
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id){
        log.info("Get user by id request received for id: {}", id);
        return new ResponseEntity<>(userService.userById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Create user request received for user: {}", user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
        log.info("Update user request received for id: {} and user: {}", id, user);
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        log.info("Delete user request received for id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}