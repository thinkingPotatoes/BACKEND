package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchArticleListResponse {
    private List<SearchArticleResponse> searchArticleResponseList;

    private long totalCnt;
    private int curPage;
}
