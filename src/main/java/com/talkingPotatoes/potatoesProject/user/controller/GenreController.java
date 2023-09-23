package com.talkingPotatoes.potatoesProject.user.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.dto.request.GenreRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.GenreResponse;
import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
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

    @PostMapping
    public ResponseEntity<Response> createGenre(@RequestBody GenreRequest genreRequest) {
        if (!genreService.createGenre(genreRequest.getGenre())){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(Response.builder()
                    .message("기존에 존재하는 장르입니다.")
                    .build());

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("장르가 정상 등록되었습니다.")
                        .build());
    }

    @DeleteMapping("/{genre-id}")
    public ResponseEntity<Response> deleteGenre(@PathVariable("genre-id") UUID id) {
        genreService.deleteGenre(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("장르기 정상 삭제되었습니다.")
                        .build());
    }
}
