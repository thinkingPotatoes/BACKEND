package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {

    private String docId;

    private String staffId;

    private String staffNm;

    private String staffRoleGroup;

    private String staffRole;

}
