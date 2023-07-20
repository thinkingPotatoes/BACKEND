package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_actor", columnList = "docId"))
public class Actor {

    @Id
    @GeneratedValue
    private long id;

    private String docId;

    private String actorNm;

    private String actorEnNm;

    private String actorId;

}
