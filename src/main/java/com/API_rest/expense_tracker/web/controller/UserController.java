package com.API_rest.expense_tracker.web.controller;

import com.API_rest.expense_tracker.persistence.entities.UserEntity;
import com.API_rest.expense_tracker.service.UserService;
import com.API_rest.expense_tracker.web.dto.UserDTO;
import com.API_rest.expense_tracker.web.dto.UserSignUpDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int elements) {
        return ResponseEntity.ok(userService.getAllUsers(page, elements));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable long idUser) {
        return ResponseEntity.ok(userService.findUserById(idUser));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserSignUpDTO userSignUpDTO) {
        UserEntity createdUser = userService.registerUser(userSignUpDTO);
        return ResponseEntity.ok(createdUser);
    }
}
