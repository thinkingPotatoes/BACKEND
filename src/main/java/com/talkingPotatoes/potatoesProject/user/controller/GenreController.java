package com.talkingPotatoes.potatoesProject.user.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.dto.response.GenreResponse;
import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<Response> getGenreList() {
        List<Genre> genreList = genreService.getGenreList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("장르리스트가 조회되었습니다.")
                        .data(GenreResponse.builder()
                                .genreList(genreList)
                                .build())
                        .build());
    }
}
