package com.messenger.queue.Controller;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import com.messenger.queue.Enums.TypeOfService;
import com.messenger.queue.Services.QueueCheckerService;
import com.messenger.queue.Services.QueueService;
import com.messenger.queue.Services.Strategies.WindowStrategy;
import com.messenger.queue.Services.TicketExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private final AtomicLong counter = new AtomicLong();
    private final QueueService queueService;
    private final TicketExecutor ticketExecutor;
    private final SimpMessagingTemplate messagingTemplate;
    private final QueueCheckerService queueCheckerService;
    private final WindowStrategy windowStrategy;

    public Controller(QueueService queueService,
                      TicketExecutor ticketExecutor,
                      SimpMessagingTemplate messagingTemplate, QueueCheckerService queueCheckerService, WindowStrategy windowStrategy) {
        this.queueService = queueService;
        this.ticketExecutor = ticketExecutor;
        this.messagingTemplate = messagingTemplate;
        this.queueCheckerService = queueCheckerService;
        this.windowStrategy = windowStrategy;
    }

    @PostMapping("/get")
    public void get(@RequestBody TicketDTO ticketDTO) {
        ticketDTO.setQueueNumber(counter.incrementAndGet());
        ticketDTO.setTimeCreated(System.currentTimeMillis());

        queueService.addTicket(ticketDTO);
        queueCheckerService.checkQueue();

        messagingTemplate.convertAndSend("/queue/tickets", queueService.getAllQueuesAsMap());

    }

    @GetMapping("/queue")
    public Object getQueue() {
        queueCheckerService.checkQueue();

        return queueService.getAllQueuesAsMap();
    }

    @PostMapping("/exec")
    public void executor(@RequestBody TicketDTO ticket) {
        TypeOfService window = windowStrategy.handle(ticket);
        ticketExecutor.execute(window);
        messagingTemplate.convertAndSend("/queue/tickets", queueService.getAllQueuesAsMap());
    }
}


