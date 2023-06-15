package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    private String docId;

    @Id
    private String staffId;

    private String staffNm;

    private String staffRoleGroup;

    private String staffRole;

}
