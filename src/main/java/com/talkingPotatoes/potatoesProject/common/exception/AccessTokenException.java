package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessTokenException  extends RuntimeException {
    public AccessTokenException(String message) {
        super(message);
        log.error("AccessTokenException:: " + message);
    }
}
