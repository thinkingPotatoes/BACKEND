package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.BlogInfoDto;
import com.talkingPotatoes.potatoesProject.user.dto.MyPageDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface MyPageService {
    UserDto getUserInfo(UUID loginId);

    BlogInfoDto getBlogInfo(UUID loginId);

    MyPageDto getMyPage(UUID loginId);

    void updatePassword(UUID loginId, String password);

    void updateNickname(UUID loginId, String nickname);

    void updateTitle(UUID loginId, String title);

    void updateGenre(UUID loginId, List<Genre> genreList);

    void update(UUID loginId, MyPageDto myPageDto);
}
