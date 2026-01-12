package com.messenger.queue.Enums;



public enum EmergencyType {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final Integer PriorityValue;

    EmergencyType( Integer priorityValue) {
        PriorityValue = priorityValue;
    }

    public Integer getPriorityValue() {
        return PriorityValue;
    }

    public EmergencyType next() {
        switch (this) {
            case LOW:
                return MEDIUM;
            case MEDIUM:
                return HIGH;

        }
        return this;
    }
}

