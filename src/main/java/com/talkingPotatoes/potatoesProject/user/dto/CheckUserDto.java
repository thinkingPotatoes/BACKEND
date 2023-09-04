package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckUserDto {
    Status check;
}
