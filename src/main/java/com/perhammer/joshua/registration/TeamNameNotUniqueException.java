package com.perhammer.joshua.registration;

public class TeamNameNotUniqueException extends RuntimeException {

    public TeamNameNotUniqueException(String message) {
        super(message);
    }
}
