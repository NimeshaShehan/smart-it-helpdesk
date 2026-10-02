package com.nimesha.helpdesk.service;

import com.nimesha.helpdesk.dto.CreateTicketRequest;
import com.nimesha.helpdesk.dto.TicketResponse;
import com.nimesha.helpdesk.entity.Ticket;
import com.nimesha.helpdesk.entity.TicketPriority;
import com.nimesha.helpdesk.entity.TicketStatus;
import com.nimesha.helpdesk.entity.User;
import com.nimesha.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

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
}