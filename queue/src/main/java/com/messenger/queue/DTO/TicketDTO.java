package com.messenger.queue.DTO;

import com.messenger.queue.Enums.EmergencyType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TicketDTO {


    private String id;
    private String serviceType;
    private EmergencyType emergencyType;
    private Long TimeCreated;


}
