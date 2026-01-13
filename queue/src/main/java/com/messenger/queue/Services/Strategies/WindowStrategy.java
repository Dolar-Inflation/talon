package com.messenger.queue.Services.Strategies;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.TypeOfService;
import com.messenger.queue.Interfaces.QueueTypeStrategy;
import org.springframework.stereotype.Component;

@Component

public class WindowStrategy implements QueueTypeStrategy {



    @Override
    public TypeOfService handle(Object object) {

        TicketDTO ticketDTO = (TicketDTO) object;


        if (ticketDTO.getServiceType().contains("SERVER")){

           return TypeOfService.FIRST_WINDOW;
        }
        else if (ticketDTO.getServiceType().toString().contains("PC")){

           return TypeOfService.SECOND_WINDOW;
        }
        else {

           return TypeOfService.THIRD_WINDOW;
        }


    }

    @Override
    public boolean supportsQueue() {
        return true;
    }
}
