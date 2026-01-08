package com.messenger.talon.Controller;

import com.messenger.talon.DTO.TicketDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {


    private RestTemplate restTemplate;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/send")
    public String sendTicket(@RequestBody TicketDTO ticketDTO) throws InterruptedException {

//        Thread.sleep(6000);
        restTemplate.postForObject( "http://queue/get", ticketDTO, String.class );

        return ""+ticketDTO;
    }
}
