package com.messenger.queue.Enums;

import javafx.scene.layout.Priority;

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