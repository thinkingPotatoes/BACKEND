package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.MovieApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieApiRepository extends JpaRepository<MovieApi, String> {
    public List<MovieApi> findAllByUpdatedAt(LocalDate updatedAt);
}
