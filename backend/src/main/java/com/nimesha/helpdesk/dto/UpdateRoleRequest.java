package com.nimesha.helpdesk.dto;

import com.nimesha.helpdesk.entity.Role;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleRequest {

    @NotNull
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}