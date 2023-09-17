package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.BlogInfoDto;
import com.talkingPotatoes.potatoesProject.user.dto.BlogUserDto;
import com.talkingPotatoes.potatoesProject.user.dto.MyPageDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface MyPageService {
    UserDto getUserInfo(UUID id);

    BlogInfoDto getBlogInfo(UUID id);

    MyPageDto getMyPage(UUID id);

    void updatePassword(UUID id, String password);

    void update(UUID id, MyPageDto myPageDto);

    BlogUserDto getBlogUser(UUID id);
}
