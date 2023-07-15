package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;

public interface EmailService {
    void sendSignUpMessage(UserDto to) throws Exception;

    void verify(String token);

    void sendEmail(String userId) throws Exception;
}
