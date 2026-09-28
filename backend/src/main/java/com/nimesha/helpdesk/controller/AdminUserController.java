package com.nimesha.helpdesk.controller;

import com.nimesha.helpdesk.dto.UpdateRoleRequest;
import com.nimesha.helpdesk.dto.UserResponse;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(
                new UserResponse(
                        updatedUser.getId(),
                        updatedUser.getName(),
                        updatedUser.getEmail(),
                        updatedUser.getRole()
                )
        );
    }
}