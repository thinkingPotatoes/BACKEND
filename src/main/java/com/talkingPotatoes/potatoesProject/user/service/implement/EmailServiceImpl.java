package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.common.util.EmailUtil;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailUtil emailUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void sendSignUpMessage(UserDto to) throws Exception {

        log.info("EmailServiceImpl::: sendSignUpMessage start");
        String message = "<div>"
                + "<h1> 안녕하세요. Filmo 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:8080/users/email-verify?token=" + emailUtil.createToken(to) + "'>인증 링크</a>"
                + "</div>";

        emailUtil.createMessage(to.getUserId(), message);
        log.info("EmailServiceImpl::: sendSignUpMessage finish");
    }

    @Override
    @Transactional
    public void verify(String token) {
        log.info("EmailServiceImpl::: verify start");

        log.info("EmailServiceImpl::: verify " + token);
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updateEmailChecked(true);
        log.info("EmailServiceImpl::: verify " + user.getId() + " email checked");

        userRepository.save(user);
        log.info("EmailServiceImpl::: verify finish");
    }

    @Override
    public void sendEmail(String userId) throws Exception {
        log.info("EmailServiceImpl::: sendEmail start");
        sendSignUpMessage(userMapper.toDto(userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."))));
        log.info("EmailServiceImpl::: sendEmail finish");
    }
}