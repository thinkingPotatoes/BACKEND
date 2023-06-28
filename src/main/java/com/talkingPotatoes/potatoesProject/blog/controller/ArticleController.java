package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.Response;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import com.talkingPotatoes.potatoesProject.blog.mapper.ArticleDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleDtoMapper articleDtoMapper;

    /* 블로그 글 등록 */
    @PostMapping("/create")
    public ResponseEntity<Response> createArticle(@RequestBody @Valid CreateArticleRequest createArticleRequest) {
        ArticleDto articleDto = articleDtoMapper.fromCreateArticleRequest(createArticleRequest);
        articleService.createArticle(articleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 등록되었습니다.")
                        .build());
    }

    /* 블로그 글 수정 */
    @PostMapping("/update")
    public ResponseEntity<Response> updateArticle(@RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        String msg = "";
        ArticleDto articleDto = articleDtoMapper.fromUpdateArticleRequest(updateArticleRequest);

        if(articleService.existArticleById(articleDto.getId())){
            /* Update Article */
            articleService.updateArticle(articleDto);
            msg = "글이 정상 업데이트되었습니다.";
        }
        else{
            /* Article Id is null */
            msg = "해당 글은 존재하지 않습니다..";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message(msg)
                        .build());
    }

    /* 블로그 글 삭제 */
    @PostMapping("/delete")
    public ResponseEntity<Response> deleteArticle(@RequestBody Map<String, String> idMap){
        String msg = "";
        UUID id = UUID.fromString(idMap.get("id"));

        if(articleService.existArticleById(id)){
            /* DELETE Article */
            articleService.deleteArticle(id);
            msg = "글이 정상 삭제되었습니다.";
        }else{
            /* Article Id is NULL */
            msg = "해당 글은 이 존재하지 않습니다.";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message(msg)
                        .build());
    }

}
