package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.common.util.EmailUtil;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    private final EmailUtil emailUtil;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void sendSignUpMessage(UserDto to) throws Exception {

        String message = "<div>"
                + "<h1> 안녕하세요. Filmo 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:8080/users/email-verify?token=" + emailUtil.createToken(to) + "'>인증 링크</a>"
                + "</div>";

        emailUtil.createMessage(to.getUserId(), message);

    }

    @Override
    public void verify(String token) {
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updateEmailChecked(true);
        userRepository.save(user);
    }
}