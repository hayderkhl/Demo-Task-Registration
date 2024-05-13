package com.example.demotaskregistration.controller;

import com.example.demotaskregistration.dto.UserDto;
import com.example.demotaskregistration.exception.EntityNotFoundException;
import com.example.demotaskregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.ok("Registration successful");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("register failed: Invalid credentials");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        try {
             return userService.login(requestMap);
             //in this case I have to return the token to use it later
            //return ResponseEntity.ok("success login");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: Invalid credentials");
        }
    }


    @GetMapping("/users/all")
    public List<UserDto> findAll() {


        try {
            return userService.findAll();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only for Admin Or your Token Expired");
        }
    }

    @GetMapping("/{userid}")
    public UserDto findAll(@PathVariable(value = "userid") Integer userid) {

        try {
            return userService.findById(userid);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Id Not Found");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID " + userId + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }

}
