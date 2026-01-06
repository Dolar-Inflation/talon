package com.messenger.talon.DTO;

import com.messenger.talon.Enums.EmergencyType;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class TicketDTO {

    private String id = UUID.randomUUID().toString();
    private String serviceType;
    private EmergencyType emergencyType;
    private Long TimeCreated=System.currentTimeMillis();




}
