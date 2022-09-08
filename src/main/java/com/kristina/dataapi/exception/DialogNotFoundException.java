package com.kristina.dataapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Dialog not found")
public class DialogNotFoundException extends RuntimeException {
    public DialogNotFoundException() {
    }

    public DialogNotFoundException(String message) {
        super(message);
    }
}
