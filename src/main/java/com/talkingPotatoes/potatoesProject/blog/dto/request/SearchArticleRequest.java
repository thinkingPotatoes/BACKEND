package com.talkingPotatoes.potatoesProject.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchArticleRequest {

    @NotBlank(message = "검색어를 입력해주세요.")
    private String keyword;
}
