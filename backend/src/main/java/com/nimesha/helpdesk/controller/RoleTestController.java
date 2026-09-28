package com.nimesha.helpdesk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/employee/test")
    public String employeeTest() {
        return "Employee access granted";
    }

    @GetMapping("/support/test")
    public String supportTest() {
        return "IT Support access granted";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin access granted";
    }
}