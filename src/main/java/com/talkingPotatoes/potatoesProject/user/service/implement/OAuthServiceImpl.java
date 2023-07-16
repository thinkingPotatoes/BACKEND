package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserGenreMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final UserGenreRepository userGenreRepository;

    private final UserMapper userMapper;
    private final UserGenreMapper userGenreMapper;

    @Override
    @Transactional
    public void oAuthContinueSignUp(UserDto userDto, List<UserGenreDto> userGenreDtoList) {
        log.info("OAuthServiceImpl:: oAuthContinueSignUp start");

        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("등록된 회원이 아닙니다."));
        user.continueSignUp(userDto.getNickname(), userDto.getTitle());
        userRepository.save(user);
        log.info("OAuthServiceImpl:: oAuthContinueSignUp " + user.getId() + " saved");

        for (UserGenreDto dto : userGenreDtoList) {
            dto.setUserId(user.getId());
        }

        userGenreRepository.saveAll(userGenreMapper.toEntity(userGenreDtoList));
        log.info("OAuthServiceImpl:: oAuthContinueSignUp " + user.getId() + " userGenre saved");

        log.info("OAuthServiceImpl:: oAuthContinueSignUp finish");
    }
}
