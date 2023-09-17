package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicationException extends RuntimeException {
    public DuplicationException(String message) {
        super(message);
        log.error("DuplicationException:: " + message);
    }
}