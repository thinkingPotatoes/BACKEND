package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InactiveException extends RuntimeException {
    public InactiveException(String message) {
        super(message);
        log.error("InactiveException:: " + message);
    }
}