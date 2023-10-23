package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
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
        String message = "<div>"
                + "<h1> 안녕하세요. Filmo 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 이메일 인증이 완료됩니다.<p>"
                + "<a href='http://localhost:5173/register/success?token=" + emailUtil.createToken(to) + "'>인증 링크</a>"
                + "</div>";

        emailUtil.createMessage(to.getUserId(), message);
    }

    @Override
    @Transactional
    public void verifyForSignUp(String token) {
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updateRole(Role.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public void sendEmailForSignUp(String userId) throws Exception {
        sendSignUpMessage(userMapper.toDto(userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."))));
    }

    @Override
    @Transactional
    public void sendEmailForPassword(String email) throws Exception {
        sendResetPasswordMessage(userMapper.toDto(userRepository.findByUserId(email).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."))));
    }

    private void sendResetPasswordMessage(UserDto toDto) throws Exception {
        String message = "<div>"
                + "<h1> 안녕하세요. Filmo 입니다</h1>"
                + "<br>"
                + "<p>아래 링크를 클릭하면 비밀번호를 재설정할 수 있습니다.<p>"
                + "<a href='localhost:5173/password/reset?token=" + emailUtil.createToken(toDto) + "'>인증 링크</a>"
                + "</div>";

        emailUtil.createMessage(toDto.getUserId(), message);
    }

    @Override
    @Transactional
    public void verifyForPassword(String token, String password) {
        String id = emailUtil.checkToken(token);

        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("회원을 찾지 못하였습니다."));
        user.updatePassword(password);

        userRepository.save(user);
    }
}
