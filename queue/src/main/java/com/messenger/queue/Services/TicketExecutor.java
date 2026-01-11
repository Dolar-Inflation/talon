package com.messenger.queue.Services;

import com.messenger.queue.Controller.Controller;
import com.messenger.queue.DTO.TicketDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketExecutor {

    private final QueueCheckerService queueCheckerService;
    private final SimpMessagingTemplate messagingTemplate;

    public TicketExecutor( QueueCheckerService queueCheckerService, SimpMessagingTemplate messagingTemplate) {
        this.queueCheckerService = queueCheckerService;

        this.messagingTemplate = messagingTemplate;
    }

    public List<TicketDTO> execute() {
        synchronized (queueCheckerService) {
            List<TicketDTO> list = queueCheckerService.checkQueue();
            list.remove(0);
//            messagingTemplate.convertAndSend("/queue/tickets", list);
            return list;
        }

    }

}
