package com.messenger.queue.Controller;

import com.messenger.queue.DTO.TicketDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/get")
    public String get(@RequestBody TicketDTO ticketDTO) {
        System.out.println("получил"+ticketDTO);
        return "Hello World! ServiceType=" + ticketDTO.getServiceType() +
                ", EmergencyType=" + ticketDTO.getEmergencyType() +
                ", Id=" + ticketDTO.getId();


    }

}
