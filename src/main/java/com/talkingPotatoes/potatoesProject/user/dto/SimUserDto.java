package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class SimUserDto {

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
