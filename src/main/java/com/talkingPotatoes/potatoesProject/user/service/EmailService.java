package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;

public interface EmailService {
    void sendSignUpMessage(UserDto to) throws Exception;

    void verifyForSignUp(String token);

    void sendEmailForSignUp(String userId) throws Exception;

    void sendEmailForPassword(String email) throws Exception;

    void verifyForPassword(String token, String password);
}
