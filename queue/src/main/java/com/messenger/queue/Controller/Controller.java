package com.messenger.queue.Controller;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
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
   private final List<TicketDTO> que = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;

    public Controller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/get")
    public void get(@RequestBody TicketDTO ticketDTO) {


        que.add(ticketDTO);
        Long ticletNumber = counter.incrementAndGet();
        ticketDTO.setQueueNumber(ticletNumber);
        Long now = System.currentTimeMillis();





            System.out.println("получил" + ticketDTO.getQueueNumber() + ticketDTO.getServiceType() + ticketDTO.getEmergencyType());

        messagingTemplate.convertAndSend("/queue/tickets", que);



    }

    @GetMapping("/queue")
    public List<TicketDTO> getQueue() {

        return que;
    }

}
