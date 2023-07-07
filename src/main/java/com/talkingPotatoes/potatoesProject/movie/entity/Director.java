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
@Table(indexes = @Index(name = "i_director", columnList = "docId"))
public class Director {

    @Id
    @GeneratedValue
    private long id;

    private String docId;

    private String directorNm;

    private String directorEnNm;

    private String directorId;

}
