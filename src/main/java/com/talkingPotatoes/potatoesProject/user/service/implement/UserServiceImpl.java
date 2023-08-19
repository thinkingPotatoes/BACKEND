package com.talkingPotatoes.potatoesProject.user.service.implement;

import java.util.List;

import com.talkingPotatoes.potatoesProject.common.exception.AccessDeniedException;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.common.jwt.JwtTokenProvider;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserGenreMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserDto signUp(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.updatePassword(encoder.encode(userDto.getPassword()));
		user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public TokenDto login(UserDto userDto) {
        User user = userRepository.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        if (!encoder.matches(userDto.getPassword(), user.getPassword())) throw new NotFoundException("사용자를 찾지 못하였습니다.");

        if (!user.isEmailChecked()) throw new AccessDeniedException("이메일을 확인해주세요");
        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getUserId(), user.getRole());

        return tokenDto;
    }
}
