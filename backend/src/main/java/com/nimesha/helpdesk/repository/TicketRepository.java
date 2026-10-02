package com.nimesha.helpdesk.repository;

import com.nimesha.helpdesk.entity.Ticket;
import com.nimesha.helpdesk.entity.TicketStatus;
import com.nimesha.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedByOrderByCreatedAtDesc(User createdBy);

    List<Ticket> findByStatusOrderByCreatedAtDesc(TicketStatus status);

    List<Ticket> findByAssignedToOrderByCreatedAtDesc(User assignedTo);
}