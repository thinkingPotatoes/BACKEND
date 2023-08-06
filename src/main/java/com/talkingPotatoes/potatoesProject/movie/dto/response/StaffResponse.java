package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffResponse {
    private String staffNm;

    private String staffRoleGroup;

    private String staffRole;
}
