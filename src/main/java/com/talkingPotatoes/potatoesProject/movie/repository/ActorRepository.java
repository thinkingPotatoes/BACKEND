package com.talkingPotatoes.potatoesProject.movie.repository;

import com.talkingPotatoes.potatoesProject.movie.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from actor where doc_id = :id", nativeQuery = true)
    void deleteByDocIdInQuery(@Param("id") String docId);
}
