package com.talkingPotatoes.potatoesProject.user.dto.response;

import com.talkingPotatoes.potatoesProject.user.dto.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckUserResponse {
    Status check;
}
