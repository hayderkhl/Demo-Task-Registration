package com.example.demotaskregistration.service;

import com.example.demotaskregistration.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    //anyone will be able to register in the application
    ResponseEntity<String> register(UserDto dto);

    //after a success login the user or the admin will get a token
    //to be able to access to other endpoints
    ResponseEntity<String> login(Map<String, String> requestMap);

    //Only the admin will be able to get all the users
    List<UserDto> findAll();

    UserDto findById(Integer userId);

    void deleteUser(Integer userId);
}
