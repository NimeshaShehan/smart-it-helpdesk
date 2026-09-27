package com.nimesha.helpdesk.dto;

import com.nimesha.helpdesk.entity.Role;

public record LoginResponse(
        String token,
        Long userId,
        String name,
        String email,
        Role role
) {
}