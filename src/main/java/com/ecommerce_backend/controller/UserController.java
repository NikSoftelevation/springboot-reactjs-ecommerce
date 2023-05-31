package com.ecommerce_backend.controller;

import com.ecommerce_backend.payload.UserDto;
import com.ecommerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        Date date = new Date();
        userDto.setCreatedAt(date);
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") int userId) {
        UserDto updatedU = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedU, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserByUserId(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allDto = userService.getAllUsers();

        return new ResponseEntity<>(allDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUserByUserId(@PathVariable("userId") int userId) {
        userService.deleteUserByUserId(userId);
    }
}