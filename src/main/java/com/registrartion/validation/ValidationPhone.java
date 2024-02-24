package com.registrartion.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidationPhone {
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+380\\d{9}$");
    }
}
