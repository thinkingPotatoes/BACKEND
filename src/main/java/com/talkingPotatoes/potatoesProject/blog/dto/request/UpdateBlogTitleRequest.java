package com.talkingPotatoes.potatoesProject.blog.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBlogTitleRequest {
    private String title;
}
