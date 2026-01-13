package com.messenger.queue.Services;

import com.messenger.queue.Controller.Controller;
import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.TypeOfService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketExecutor {

    private final QueueCheckerService queueCheckerService;
    private final SimpMessagingTemplate messagingTemplate;
    private final QueueService queueService;

    public TicketExecutor(QueueCheckerService queueCheckerService, SimpMessagingTemplate messagingTemplate, QueueService queueService) {
        this.queueCheckerService = queueCheckerService;

        this.messagingTemplate = messagingTemplate;
        this.queueService = queueService;
    }

    public TicketDTO execute(TypeOfService window) {


        queueCheckerService.checkQueue();


        TicketDTO removed = queueService.removeFromQueue(window);


        Map<String, List<TicketDTO>> result = new HashMap<>();
        result.put("queue1", queueService.getAllTickets());
        result.put("queue2", queueService.getQueue2());
        result.put("queue3", queueService.getQueue3());

        messagingTemplate.convertAndSend("/queue/tickets", result);

        return removed;
    }


}
