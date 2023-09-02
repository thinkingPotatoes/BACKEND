package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.dto.BlogInfoDto;
import com.talkingPotatoes.potatoesProject.user.dto.MyPageDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.entity.UserGenre;
import com.talkingPotatoes.potatoesProject.user.mapper.UserMapper;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreQueryRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import com.talkingPotatoes.potatoesProject.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class MyPageServiceImpl implements MyPageService {
    private final UserRepository userRepository;
    private final UserGenreRepository userGenreRepository;
    private final UserGenreQueryRepository userGenreQueryRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public UserDto getUserInfo(UUID loginId) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        return userMapper.toDto(user);
    }

    @Override
    public BlogInfoDto getBlogInfo(UUID loginId) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        List<String> genreList = userGenreQueryRepository.findByUserId(loginId);

        BlogInfoDto blogInfoDto = BlogInfoDto.builder()
                .title(user.getTitle())
                .genreNameList(genreList)
                .build();

        return blogInfoDto;
    }

    @Override
    public MyPageDto getMyPage(UUID loginId) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        List<String> genreList = userGenreQueryRepository.findByUserId(loginId);

        MyPageDto myPageDto = MyPageDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .title(user.getTitle())
                .genreNameList(genreList)
                .platform(user.getPlatform())
                .build();

        return myPageDto;
    }


    @Override
    @Transactional
    public void updatePassword(UUID loginId, String password) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        user.updatePassword(encoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(UUID loginId, MyPageDto myPageDto) {
        updateNickname(loginId, myPageDto.getNickname());
        updateTitle(loginId, myPageDto.getTitle());
        updateGenre(loginId, myPageDto.getGenreList());
    }

    @Transactional
    void updateNickname(UUID loginId, String nickname) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        user.updateNickname(nickname);
        userRepository.save(user);
    }

    @Transactional
    void updateTitle(UUID loginId, String title) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        user.updateTitle(title);
        userRepository.save(user);
    }

    @Transactional
    void updateGenre(UUID loginId, List<Genre> genreList) {
        // 기존 정보 전부 삭제하고 다시 등록
        userGenreRepository.deleteByUserId(loginId);

        for (Genre genre : genreList) {
            userGenreRepository.save(UserGenre.builder()
                    .userId(loginId)
                    .genreId(genre.getId())
                    .build());
        }
    }

}
