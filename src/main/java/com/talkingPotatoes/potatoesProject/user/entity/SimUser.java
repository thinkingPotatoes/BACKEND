package com.talkingPotatoes.potatoesProject.user.entity;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SimUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID id;

    private String userId;

    private UUID top1;

    private UUID top2;

    private UUID top3;

    private UUID top4;

    private UUID top5;

    private UUID top6;

    private UUID top7;

    private UUID top8;

    private UUID top9;

    private UUID top10;
}
