package com.nimesha.helpdesk.dto;

import com.nimesha.helpdesk.entity.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
}