package com.talkingPotatoes.potatoesProject.user.service.implement;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.repository.GenreRepository;
import com.talkingPotatoes.potatoesProject.user.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getGenreList() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public boolean createGenre(String genreNm){
        if(!genreNm.isEmpty() && !genreRepository.existsByGenre(genreNm)) {
            genreRepository.save(Genre.builder().genre(genreNm).build());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteGenre(UUID genreId){
        genreRepository.deleteById(genreId);
    }
}
