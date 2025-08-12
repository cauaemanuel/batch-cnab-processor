package com.transaction_processor.backend.exception;

import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(JobInstanceAlreadyCompleteException.class)
    private ResponseEntity<Object> handleException(
            JobInstanceAlreadyCompleteException exception
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("O arquivo ja foi processado anteriormente. " +
                "Por favor, verifique o arquivo e tente novamente.");

    };
}
