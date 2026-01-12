package com.messenger.queue.Controller;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import com.messenger.queue.Services.QueueCheckerService;
import com.messenger.queue.Services.QueueService;
import com.messenger.queue.Services.TicketExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private final AtomicLong counter = new AtomicLong();
    private final QueueService queueService;
    private final TicketExecutor ticketExecutor;
    private final SimpMessagingTemplate messagingTemplate;
    private final QueueCheckerService queueCheckerService;

    public Controller(QueueService queueService,
                      TicketExecutor ticketExecutor,
                      SimpMessagingTemplate messagingTemplate, QueueCheckerService queueCheckerService) {
        this.queueService = queueService;
        this.ticketExecutor = ticketExecutor;
        this.messagingTemplate = messagingTemplate;
        this.queueCheckerService = queueCheckerService;
    }

    @PostMapping("/get")
    public void get(@RequestBody TicketDTO ticketDTO) {
        ticketDTO.setQueueNumber(counter.incrementAndGet());
        ticketDTO.setTimeCreated(System.currentTimeMillis());

        queueService.addTicket(ticketDTO);
        queueCheckerService.checkQueue();
        messagingTemplate.convertAndSend("/queue/tickets", queueService.getAllTickets());
    }

    @GetMapping("/queue")
    public List<TicketDTO> getQueue() {
        queueCheckerService.checkQueue();

        return queueService.getAllTickets();
    }

    @PostMapping("/exec")
    public void executor() {
        List<TicketDTO> list = ticketExecutor.execute();
        messagingTemplate.convertAndSend("/queue/tickets", list);
    }
}

