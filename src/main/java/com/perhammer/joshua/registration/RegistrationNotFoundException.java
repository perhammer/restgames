package com.perhammer.joshua.registration;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(Long id) {
        super("Registration id "+id+" not found");
    }
}
