package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetArticleListResponse {
    private List<GetArticleResponse> getArticleResponseList;

    private long totalCnt;
    private int curPage;
}