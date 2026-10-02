package com.nimesha.helpdesk.service;

import com.nimesha.helpdesk.dto.CreateTicketRequest;
import com.nimesha.helpdesk.dto.TicketResponse;
import com.nimesha.helpdesk.entity.Ticket;
import com.nimesha.helpdesk.entity.TicketPriority;
import com.nimesha.helpdesk.entity.TicketStatus;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketResponse createTicket(
            CreateTicketRequest request,
            User currentUser) {

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());

        ticket.setPriority(
                request.getPriority() != null
                        ? request.getPriority()
                        : TicketPriority.MEDIUM
        );

        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedBy(currentUser);

        Ticket savedTicket = ticketRepository.save(ticket);

        return toResponse(savedTicket);
    }

    public List<TicketResponse> getMyTickets(User currentUser) {

        return ticketRepository
                .findByCreatedByOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TicketResponse getTicketById(
            Long id,
            User currentUser) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        boolean isOwner =
                ticket.getCreatedBy()
                        .getId()
                        .equals(currentUser.getId());

        boolean isSupport =
                currentUser.getRole().name().equals("IT_SUPPORT")
                        || currentUser.getRole().name().equals("ADMIN");

        if (!isOwner && !isSupport) {
            throw new RuntimeException("Access denied");
        }

        return toResponse(ticket);
    }

    public TicketResponse toResponse(Ticket ticket) {

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getCreatedBy().getId(),
                ticket.getCreatedBy().getName(),
                ticket.getAssignedTo() != null
                        ? ticket.getAssignedTo().getId()
                        : null,
                ticket.getAssignedTo() != null
                        ? ticket.getAssignedTo().getName()
                        : null,
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getResolvedAt()
        );
    }
    public List<TicketResponse> getOpenTickets() {

    return ticketRepository
            .findByStatusOrderByCreatedAtDesc(TicketStatus.OPEN)
            .stream()
            .map(this::toResponse)
            .toList();
    }
    public TicketResponse assignTicketToCurrentSupport(
        Long ticketId,
        User currentUser) {

    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() ->
                    new RuntimeException("Ticket not found"));

    ticket.setAssignedTo(currentUser);

    if (ticket.getStatus() == TicketStatus.OPEN) {
        ticket.setStatus(TicketStatus.IN_PROGRESS);
    }

    Ticket updatedTicket = ticketRepository.save(ticket);

    return toResponse(updatedTicket);
}

public TicketResponse updateTicketStatus(
        Long ticketId,
        TicketStatus status) {

    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() ->
                    new RuntimeException("Ticket not found"));

    ticket.setStatus(status);

    if (status == TicketStatus.RESOLVED) {
        ticket.setResolvedAt(java.time.LocalDateTime.now());
    } else {
        ticket.setResolvedAt(null);
    }

    Ticket updatedTicket = ticketRepository.save(ticket);

    return toResponse(updatedTicket);
}
}
