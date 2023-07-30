package com.talkingPotatoes.potatoesProject.blog.dto.response;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchArticleResponse {
    private List<ArticleSearchDto> articleSearchDtoList;

    private long totalCnt;
    private int curPage;
}
