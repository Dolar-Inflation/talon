package com.messenger.queue.Interfaces;

import com.messenger.queue.DTO.TicketDTO;
import com.messenger.queue.Enums.TypeOfService;

public interface QueueTypeStrategy {

    public TypeOfService handle(Object object );
    public boolean supportsQueue();

}
