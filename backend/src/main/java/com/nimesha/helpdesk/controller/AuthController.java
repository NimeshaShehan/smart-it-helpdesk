package com.nimesha.helpdesk.controller;

import com.nimesha.helpdesk.dto.LoginRequest;
import com.nimesha.helpdesk.dto.LoginResponse;
import com.nimesha.helpdesk.dto.RegisterRequest;
import com.nimesha.helpdesk.dto.UserResponse;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}