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
@Table(indexes = @Index(name = "idx_still", columnList = "docId"))
public class Still {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String docId;

    private String stillUrl;

}
