package com.backend.clinicaodontologica.exceptions;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;


public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
