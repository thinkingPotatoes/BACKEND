package com.talkingPotatoes.potatoesProject.user.repository;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    void deleteById(UUID genreId);

    boolean existsByGenre(String genre);
}