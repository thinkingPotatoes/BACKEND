package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface BlogService {
    BlogDto get(UUID loginId);

    void updateTitle(UUID loginId, String title);

    void updateGenre(UUID loginId, List<Genre> genreList);

    void update(BlogDto fromBlogRequest);
}
