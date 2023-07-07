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
@Table(indexes = @Index(name = "i_staff", columnList = "docId"))
public class Staff {

    @Id
    @GeneratedValue
    private long id;

    private String docId;

    private String staffId;

    private String staffNm;

    private String staffRoleGroup;

    private String staffRole;

}
