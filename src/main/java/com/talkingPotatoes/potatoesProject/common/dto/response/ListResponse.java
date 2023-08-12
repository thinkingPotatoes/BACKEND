package com.talkingPotatoes.potatoesProject.common.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListResponse<D> {
    private D list;
    private long totalCnt;
    private int curPage;
}
