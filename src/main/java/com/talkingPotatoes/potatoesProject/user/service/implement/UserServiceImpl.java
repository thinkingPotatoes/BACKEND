package com.talkingPotatoes.potatoesProject.user.service.implement;


import com.talkingPotatoes.potatoesProject.common.exception.AccessDeniedException;
import com.talkingPotatoes.potatoesProject.common.exception.DuplicationException;
import com.talkingPotatoes.potatoesProject.common.exception.InactiveException;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.common.jwt.JwtTokenProvider;
import com.talkingPotatoes.potatoesProject.user.dto.*;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
import com.talkingPotatoes.potatoesProject.user.entity.SimUser;
import com.talkingPotatoes.potatoesProject.user.mapper.SimUserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.SimUserRepository;
import com.talkingPotatoes.potatoesProject.user.service.RandomNickname;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SimUserRepository simUserRepository;
    private final SimUserMapper simUserMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;

    private final RandomNickname randomNickname;

    @Override
    @Transactional
    public UserDto signUp(UserDto userDto) {
        userDto.setPlatform(Platform.NONE);

        if (userRepository.existsUserByUserIdAndRole(userDto.getUserId(), Role.INACTIVE))
            throw new InactiveException("이메일 인증이 필요합니다.");
        if (userRepository.existsUserByUserIdAndRole(userDto.getUserId(), Role.ACTIVE))
            throw new DuplicationException("이메일 중복입니다.");

        userDto.setRole(Role.ACTIVE);

        String nickname = "F_"+ randomNickname.makeNickname();
        while(userRepository.existsByNickname(nickname)) {
            nickname = "F_"+ randomNickname.makeNickname();
        }
        userDto.setNickname(nickname);
        userDto.setTitle(userDto.getNickname() + "'s Filog");

        userDto.setPassword(encoder.encode(userDto.getPassword()));

        User user = userRepository.save(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }

    @Override
    public TokenDto login(UserDto userDto) {
        User user = userRepository.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        if (!encoder.matches(userDto.getPassword(), user.getPassword()))
            throw new NotFoundException("사용자를 찾지 못하였습니다.");

        if (user.getRole() == Role.INACTIVE)
            throw new AccessDeniedException("이메일을 확인해주세요");

        if (user.getRole() == Role.WITHDRAWAL)
            throw new AccessDeniedException("회원이 탈퇴하었습니다.");

        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole());

        return tokenDto;
    }

    @Override
    @Transactional
    public TokenDto refreshToken(String refreshToken) {
        if (!jwtTokenProvider.existsRefreshToken(refreshToken))
            throw new NotFoundException("재로그인이 필요합니다.");

        String refreshUserId = jwtTokenProvider.getUserId(jwtTokenProvider.getClaimsFromToken(refreshToken));
        User user = userRepository.findById(UUID.fromString(refreshUserId))
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole());

        return tokenDto;
    }

    @Override
    public CheckUserDto checkUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            return CheckUserDto.builder()
                    .check(Status.WAITED)
                    .build();
        } else {
            if (user.get().getRole() == Role.INACTIVE) {
                return CheckUserDto.builder()
                        .check(Status.INACTIVE)
                        .build();
            } else if (user.get().getRole() == Role.WITHDRAWAL) {
                return CheckUserDto.builder()
                        .check(Status.WITHDRAWAL)
                        .build();
            } else {
                return CheckUserDto.builder()
                        .check(Status.ACTIVE)
                        .build();
            }
        }
    }

    @Override
    public void withdraw(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        user.updateWithdraw();

        userRepository.save(user);
    }

    @Override
    public List<SimUserDto> selectSimUser(String userId) {
        List<SimUser> simUserList = simUserRepository.getSimUsersByUserId(userId);

        return simUserMapper.toDto(simUserList);
    }

    @Override
    public TokenDto initMovieToken(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾지 못하였습니다."));

        if (user.getRole() == Role.INACTIVE)
            throw new AccessDeniedException("이메일을 확인해주세요");

        if (user.getRole() == Role.WITHDRAWAL)
            throw new AccessDeniedException("회원이 탈퇴하었습니다.");

        TokenDto tokenDto = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRole());

        return tokenDto;
    }
}
