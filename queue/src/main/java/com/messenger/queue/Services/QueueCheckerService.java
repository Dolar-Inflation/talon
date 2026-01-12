package com.messenger.queue.Services;

import com.messenger.queue.Controller.Controller;
import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class QueueCheckerService {

   private final QueueService queueService;
   private final SimpMessagingTemplate messagingTemplate;

    public QueueCheckerService(QueueService queueService, SimpMessagingTemplate messagingTemplate) {
        this.queueService = queueService;

        this.messagingTemplate = messagingTemplate;
    }
//TODO разобраться с сортирокой и отправкой списка в вебсокет
    @Scheduled(fixedRate = 10000)
    public List<TicketDTO> checkQueue() {
        Long now = System.currentTimeMillis();
        List<TicketDTO> tickets = queueService.getAllTickets();





        for (TicketDTO ticket : tickets) {

            Long created = ticket.getTimeCreated();
            if (now - created > 10000 && ticket.getEmergencyType() != EmergencyType.HIGH) {
                ticket.setEmergencyType(ticket.getEmergencyType().next());
                ticket.setTimeCreated(System.currentTimeMillis());


                System.out.println("Повышен приоритет: " + ticket.getQueueNumber() + " " + ticket.getServiceType() + " " + ticket.getEmergencyType());


            }
            else {
                System.out.println("<UNK> <UNK>: "+tickets + ticket.getQueueNumber() + " " + ticket.getServiceType()+""+ticket.getEmergencyType());
            }

        }
        queueService.sortTickets();
        messagingTemplate.convertAndSend("/queue/tickets", queueService.getAllTickets());

        return tickets;
    }


}
