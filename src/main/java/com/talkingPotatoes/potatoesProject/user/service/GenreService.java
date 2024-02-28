package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    List<Genre> getGenreList();

    boolean createGenre(String genre);

    void deleteGenre(UUID genreId);
}
