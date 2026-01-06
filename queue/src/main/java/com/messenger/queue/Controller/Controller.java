package com.messenger.queue.Controller;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    @PostMapping("/get")
    public String get(@RequestBody TicketDTO ticketDTO) {

        List<TicketDTO> que = new ArrayList<>();
        que.add(ticketDTO);
        AtomicLong counter = new AtomicLong();
        counter.incrementAndGet();
        Long now = System.currentTimeMillis();

//        TicketDTO firstel=que.get(0);

        for(TicketDTO ticket : que) {
            Long created = ticketDTO.getTimeCreated();
            if (now - created > 5000 && ticket.getEmergencyType() != EmergencyType.HIGH) {
                ticket.setEmergencyType(ticket.getEmergencyType().next());
                ticket.setTimeCreated(System.currentTimeMillis());
            }
        }
        System.out.println("получил"+counter+ticketDTO);
        return "Hello World! ServiceType=" + ticketDTO.getServiceType() +
                ", EmergencyType=" + ticketDTO.getEmergencyType() +
                ", Id=" + ticketDTO.getId();


    }

}
