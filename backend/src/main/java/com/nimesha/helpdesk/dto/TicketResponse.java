package com.nimesha.helpdesk.dto;

import com.nimesha.helpdesk.entity.TicketCategory;
import com.nimesha.helpdesk.entity.TicketPriority;
import com.nimesha.helpdesk.entity.TicketStatus;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String description,
        TicketCategory category,
        TicketPriority priority,
        TicketStatus status,
        Long createdById,
        String createdByName,
        Long assignedToId,
        String assignedToName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime resolvedAt
) {
}