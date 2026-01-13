package com.messenger.queue.Services;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.TypeOfService;
import com.messenger.queue.Services.Strategies.WindowStrategy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QueueService {
    private final List<TicketDTO> queue =new ArrayList<>();
    private final List<TicketDTO> queue2 = new ArrayList<>();
    private final List<TicketDTO> queue3 = new ArrayList<>();
    private final WindowStrategy windowStrategy;

    public QueueService(WindowStrategy windowStrategy) {
        this.windowStrategy = windowStrategy;
    }

    public synchronized void addTicket(TicketDTO ticket) {

        TypeOfService window = windowStrategy.handle(ticket);
        switch (window) {
            case FIRST_WINDOW -> queue.add(ticket);
            case SECOND_WINDOW -> queue2.add(ticket);
            case THIRD_WINDOW -> queue3.add(ticket);
        }




    }
    public synchronized TicketDTO removeFromQueue(TypeOfService window) {
        return switch (window) {
            case FIRST_WINDOW -> queue.isEmpty() ? null : queue.remove(0);
            case SECOND_WINDOW -> queue2.isEmpty() ? null : queue2.remove(0);
            case THIRD_WINDOW -> queue3.isEmpty() ? null : queue3.remove(0);
        };
    }
    public synchronized Map<String, List<TicketDTO>> getAllQueuesAsMap() {
        Map<String, List<TicketDTO>> result = new HashMap<>();
        result.put("queue1", new ArrayList<>(queue));
        result.put("queue2", new ArrayList<>(queue2));
        result.put("queue3", new ArrayList<>(queue3));
        return result;
    }


    public synchronized List<TicketDTO> getAllTickets() {return new ArrayList<>(queue);}
    public synchronized List<TicketDTO> getQueue2() { return new ArrayList<>(queue2); }
    public synchronized List<TicketDTO> getQueue3() { return new ArrayList<>(queue3); }


//    public synchronized void removeTicket() {
//        queue.remove(0);
//    }


    public synchronized void sortTickets() {
        queue.sort(Comparator.comparingInt(t-> t.getEmergencyType().getPriorityValue()));
        queue2.sort(Comparator.comparingInt(t-> t.getEmergencyType().getPriorityValue()));
        queue3.sort(Comparator.comparingInt(t-> t.getEmergencyType().getPriorityValue()));

    }
}
