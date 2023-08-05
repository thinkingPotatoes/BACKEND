package com.talkingPotatoes.potatoesProject.blog.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.talkingPotatoes.potatoesProject.blog.entity.Scope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateArticleRequest {

    @NotBlank(message = "영화는 필수 입력사항입니다.")
    private String movieId;

    private String subject;

    private String content;

    @NotNull(message = "평점은 필수 입력사항입니다.")
    private Integer grade;

    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    @NotNull(message = "영화 관람 날짜는 필수 입력사항입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime watchedAt;

}
