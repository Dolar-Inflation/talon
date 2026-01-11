package com.messenger.queue.Services;

import com.messenger.queue.Controller.Controller;
import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.EmergencyType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueCheckerService {

   private final QueueService queueService;

    public QueueCheckerService( QueueService queueService) {
        this.queueService = queueService;

    }
//TODO приоритет переходит с Low на High не проходя этап Medium + добавить сортировку по типу приоритета
    @Scheduled(fixedRate = 10000)
    public List<TicketDTO> checkQueue() {
        Long now = System.currentTimeMillis();
        List<TicketDTO> tickets = queueService.getAllTickets();

        for (TicketDTO ticket : tickets) {
            Long created = ticket.getTimeCreated();
            if (now - created > 100000 && ticket.getEmergencyType() != EmergencyType.HIGH) {
                ticket.setEmergencyType(ticket.getEmergencyType().next());
                ticket.setTimeCreated(System.currentTimeMillis());
//                System.out.println("получил"+ticket.getQueueNumber()+ticket.getServiceType()+ticket.getEmergencyType());
                System.out.println("Повышен приоритет: " + ticket.getQueueNumber() + " " + ticket.getServiceType() + " " + ticket.getEmergencyType());

            }
            else {
                System.out.println("<UNK> <UNK>: "+tickets + ticket.getQueueNumber() + " " + ticket.getServiceType()+""+ticket.getEmergencyType());
            }
        }
        return tickets;
    }
}
