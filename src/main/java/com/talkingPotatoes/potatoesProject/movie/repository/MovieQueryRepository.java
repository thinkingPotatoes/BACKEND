package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieQueryRepository {
    Page<Movie> findByKeyword(String keyword, Pageable pageable);

    Page<Movie> get(Pageable pageable);
}
