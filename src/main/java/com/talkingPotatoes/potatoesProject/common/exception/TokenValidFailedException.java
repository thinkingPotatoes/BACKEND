package com.talkingPotatoes.potatoesProject.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenValidFailedException extends RuntimeException {
    public TokenValidFailedException(String message) {
        super(message);
        log.error("TokenValidFailedException:: " + message);
    }
}
