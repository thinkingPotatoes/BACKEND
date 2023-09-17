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
@Table(indexes = {@Index(name = "idx_staff", columnList = "docId"),
        @Index(name = "idx_search_staff", columnList = "docId, staffNM")
})
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String docId;

    private String staffId;

    private String staffNm;

    private String staffRoleGroup;

    private String staffRole;

}
