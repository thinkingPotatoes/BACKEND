package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.BoxOfficeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoxOfficeRateRepository extends JpaRepository<BoxOfficeRate, Long> {
    List<BoxOfficeRate> findAllByTargetDt(String targetDt);
}
