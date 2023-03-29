package com.example.user.service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CRUD

    public List<User> allUser(){
        return userRepository.findAll();
    }

    public Optional<User> userById(String userId){
        return userRepository.findUserByUserId(userId);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(String id, User user){
        user.setUserId(id);
        return userRepository.save(user);
    }

    public void deleteUser(String userId){
        userRepository.deleteUserByUserId(userId);
    }
}
