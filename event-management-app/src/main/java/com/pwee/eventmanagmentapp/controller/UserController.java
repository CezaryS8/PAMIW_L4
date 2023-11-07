package com.pwee.eventmanagmentapp.controller;

import com.pwee.eventmanagmentapp.dto.UserDto;
import com.pwee.eventmanagmentapp.entity.User;
import com.pwee.eventmanagmentapp.exception.UserNotFoundException;
import com.pwee.eventmanagmentapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Validated
class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        try {
            UserDto user = userService.getUserDtoById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserDto savedUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserDto updatedUser = userService.updateUserWithoutPasswd(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long userId) {
        boolean deletionStatus = userService.deleteUser(userId);
        if (deletionStatus) {
            return ResponseEntity.ok(true); // Return true if the deletion was successful
        } else {
            return ResponseEntity.ok(false); // Return false if the deletion failed
        }
    }
}
