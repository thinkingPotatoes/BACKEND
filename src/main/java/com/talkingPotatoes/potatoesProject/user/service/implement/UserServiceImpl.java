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
    private final UserGenreRepository userGenreRepository;
    private final UserMapper userMapper;
    private final UserGenreMapper userGenreMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserDto signUp(UserDto userDto,
                          List<UserGenreDto> userGenreDtoList) {
        log.info("UserServiceImpl::: signUp start");

        User request = userMapper.toEntity(userDto);
        request.updatePassword(encoder.encode(userDto.getPassword()));
        User user = userRepository.save(request);
        log.info("UserServiceImpl::: signUp " + user.getId() + " saved");

        for (UserGenreDto dto : userGenreDtoList) {
            dto.setUserId(user.getId());
        }

        userGenreRepository.saveAll(userGenreMapper.toEntity(userGenreDtoList));
        log.info("UserServiceImpl::: signUp " + user.getId() + " userGenre saved");

        log.info("UserServiceImpl::: signUp finish");
        return userMapper.toDto(user);
    }

    @Override
    public TokenDto login(UserDto userDto) {
        log.info("UserServiceImpl::: login start");

        User user = userRepository.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        if (!encoder.matches(userDto.getPassword(), user.getPassword())) throw new NotFoundException("사용자를 찾지 못하였습니다.");

        if (!user.isEmailChecked()) throw new AccessDeniedException("이메일을 확인해주세요");

        log.info("UserServiceImpl::: login " + String.valueOf(user.getId()) + " " + user.getUserId() + " " + user.getRole());
        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getUserId(), user.getRole());

        log.info("UserServiceImpl::: login finish");
        return tokenDto;
    }
}
