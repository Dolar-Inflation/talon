package com.messenger.queue.Enums;



public enum EmergencyType {
    HIGH,
    MEDIUM,
    LOW;

    public EmergencyType next() {
        switch (this) {
            case LOW:
                return MEDIUM;
            case MEDIUM:
                return HIGH;
            default:
                return HIGH;
        }
    }
    }