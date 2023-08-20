package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class MyPageServiceImpl implements MyPageService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto get(UUID loginId) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateNickname(UUID loginId, String nickname) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        user.updateNickname(nickname);
        User resultUser = userRepository.save(user);
        return userMapper.toDto(resultUser);
    }
}
