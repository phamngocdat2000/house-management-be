package com.student.searchroom.controller;

import com.student.searchroom.entity.User;
import com.student.searchroom.model.request.RegisterUserRequest;
import com.student.searchroom.model.request.UpdateUserRequest;
import com.student.searchroom.model.response.UserResponse;
import com.student.searchroom.service.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registrationUser(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(userService.registrationUser(request));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getProfileByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getProfileByUsername(username));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }



}
