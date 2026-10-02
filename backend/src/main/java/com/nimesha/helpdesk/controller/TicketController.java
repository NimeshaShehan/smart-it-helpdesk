package com.nimesha.helpdesk.controller;

import com.nimesha.helpdesk.dto.CreateTicketRequest;
import com.nimesha.helpdesk.dto.TicketResponse;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        TicketResponse response =
                ticketService.createTicket(request, currentUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}