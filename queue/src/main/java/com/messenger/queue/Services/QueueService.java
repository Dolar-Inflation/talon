package com.messenger.queue.Services;

import com.messenger.queue.DTO.TicketDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class QueueService {
    private final List<TicketDTO> queue =new ArrayList<TicketDTO>();


    public synchronized void addTicket(TicketDTO ticket) {
        queue.add(ticket);
    }

    public synchronized List<TicketDTO> getAllTickets() {

        return new ArrayList<>(queue);

    }
    public synchronized void removeTicket() {
        queue.remove(0);
    }


    public synchronized void sortTickets() {
        queue.sort(Comparator.comparingInt(t-> t.getEmergencyType().getPriorityValue()));

    }
}
