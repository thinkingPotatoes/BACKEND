package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
}
