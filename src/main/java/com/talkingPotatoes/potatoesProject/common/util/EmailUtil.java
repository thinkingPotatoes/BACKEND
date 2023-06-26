package com.talkingPotatoes.potatoesProject.common.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.talkingPotatoes.potatoesProject.common.exception.EmailException;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailUtil {
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    public void createMessage(String to, String message) throws Exception {
        try {
            MimeMessage sendMessage = emailSender.createMimeMessage();

            sendMessage.addRecipients(Message.RecipientType.TO, to);
            sendMessage.setSubject("Filmo 회원가입 이메일 인증");

            sendMessage.setText(message, "utf-8", "html");
            sendMessage.setFrom(new InternetAddress("bitcamp1@naver.com", "Filmo"));

            emailSender.send(sendMessage);

        } catch (MessagingException | UnsupportedEncodingException ex) {
            throw new EmailException("이메일 생성에 실패했습니다.");
        }
    }

    public String createToken(UserDto dto) {
        UUID random = UUID.randomUUID();

        redisUtil.setDataExpire(String.valueOf(random), String.valueOf(dto.getId()), 60);

        return String.valueOf(random);
    }

    public String checkToken(String token) {
        String id = redisUtil.getData(token);
        if (id.isEmpty()) throw new EmailException("이메일 인증에 실패했습니다.");
        else return id;
    }
}