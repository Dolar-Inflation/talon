package com.messenger.queue.Services;

import com.messenger.queue.Controller.Controller;
import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QueueCheckerService {
//TODO создать несколько отдельных html страничек + реализовать более адекватный вариант нескольких очередей (без костыльного создания нескольких массивов и нескольких атомарных счётчиков)
    @Autowired
    private final QueueService queueService;
    private final SimpMessagingTemplate messagingTemplate;

    public QueueCheckerService(QueueService queueService, SimpMessagingTemplate messagingTemplate) {
        this.queueService = queueService;

        this.messagingTemplate = messagingTemplate;
    }


    @Scheduled(fixedRate = 10000)
    public void checkQueue() {


        List<TicketDTO> ticket1 = queueService.getAllTickets();
        List<TicketDTO> ticket2 = queueService.getQueue2();
        List<TicketDTO> ticket3 = queueService.getQueue3();



        check(ticket1);
        check(ticket2);
        check(ticket3);
        queueService.sortTickets();

        Map<String, List<TicketDTO>> result = queueService.getAllQueuesAsMap();


        messagingTemplate.convertAndSend("/queue/tickets", result);


    }

    public void check(List<TicketDTO> tickets) {
        Long now = System.currentTimeMillis();
        for (TicketDTO ticket : tickets) {

            Long created = ticket.getTimeCreated();
            if (now - created > 10000 && ticket.getEmergencyType() != EmergencyType.HIGH) {
                ticket.setEmergencyType(ticket.getEmergencyType().next());
                ticket.setTimeCreated(System.currentTimeMillis());


                System.out.println("Повышен приоритет: " + ticket.getQueueNumber() + " " + ticket.getServiceType() + " " + ticket.getEmergencyType());


            } else {
                System.out.println("<UNK> <UNK>: " + tickets + ticket.getQueueNumber() + " " + ticket.getServiceType() + " " + ticket.getEmergencyType());
            }

        }

    }
}
