package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateUserGenreRequest;
import com.talkingPotatoes.potatoesProject.blog.entity.Genre;
import com.talkingPotatoes.potatoesProject.blog.entity.UserGenre;
import com.talkingPotatoes.potatoesProject.blog.repository.UserGenreQueryRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.UserGenreRepository;
import com.talkingPotatoes.potatoesProject.blog.service.BlogService;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class BlogServiceImpl implements BlogService {

    private final UserRepository userRepository;
    private final UserGenreRepository userGenreRepository;
    private final UserGenreQueryRepository userGenreQueryRepository;

    @Override
    public BlogDto get(UUID loginId) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        List<String> genreList = userGenreQueryRepository.findByUserId(loginId);

        BlogDto blogDto = BlogDto.builder()
                .title(user.getTitle())
                .genreNameList(genreList)
                .build();

        return blogDto;
    }

    @Override
    public void updateTitle(UUID loginId, String title) {
        User user = userRepository.findById(loginId).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        user.updateTitle(title);
        userRepository.save(user);
    }

    @Override
    public void updateGenre(UUID loginId, List<Genre> genreList) {
        // 기존 정보 전부 삭제하고 다시 등록
        userGenreRepository.deleteByUserId(loginId);

        for (Genre genre : genreList) {
            userGenreRepository.save(UserGenre.builder()
                    .userId(loginId)
                    .genreId(genre.getId())
                    .build());
        }
    }

    @Override
    public void update(BlogDto blogDto) {
        updateTitle(blogDto.getUserId(), blogDto.getTitle());
        updateGenre(blogDto.getUserId(), blogDto.getGenreList());
    }
}
