package com.nimesha.helpdesk.dto;

import com.nimesha.helpdesk.entity.TicketStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateTicketStatusRequest {

    @NotNull
    private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}