package com.nimesha.helpdesk.service;

import com.nimesha.helpdesk.dto.LoginRequest;
import com.nimesha.helpdesk.dto.LoginResponse;
import com.nimesha.helpdesk.dto.RegisterRequest;
import com.nimesha.helpdesk.entity.Role;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.repository.UserRepository;
import com.nimesha.helpdesk.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.EMPLOYEE
        );

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}