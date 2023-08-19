package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
        log.error("AccessDeniedException:: " + message);
    }
}
