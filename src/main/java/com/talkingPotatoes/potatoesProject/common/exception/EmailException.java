package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
        log.error("EmailException:: " + message);
    }
}
