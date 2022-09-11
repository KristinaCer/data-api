package com.kristina.dataapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = DialogNotFoundException.class)
    public ResponseEntity<Object> handleUserApiRequestException(DialogNotFoundException e) {
        //1.create a payload containing exception details that we will send in the response entity
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                 e,
                notFound,
                ZonedDateTime.now(ZoneId.of("Z")));
        //2.Return response entity
        return new ResponseEntity<>(apiException, notFound);
    }
}
