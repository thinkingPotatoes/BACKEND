package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
public class SimUserDto {

    private String userId;

    private List<UUID> topList;
}
