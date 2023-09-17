package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StarRatingRepository extends JpaRepository<StarRating, Long> {
    List<StarRating> searchStarRatingByUserId(UUID userId);
}
