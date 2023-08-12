package com.talkingPotatoes.potatoesProject.common.exception;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Response> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({EmailException.class})
    protected ResponseEntity<Response> handleEmailException(EmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Response> handleServerException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder()
                        .message(ex.getMessage())
                        .build());
    }
}
