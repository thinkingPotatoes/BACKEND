package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.user.service.RandomNickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomNicknameImpl implements RandomNickname {

    @Override
    public String makeNickname() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 4;
        Random random = new Random();
        String nickname = random.ints(leftLimit, rightLimit+1)
                .filter(i -> (i<=57 || i>=65) && (i<=90 || i>=97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return nickname;
    }
}
