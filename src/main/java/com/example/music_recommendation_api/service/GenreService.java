package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow();
    }

    public List<Genre> getAllGenres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genrePage = genreRepository.findAll(pageable);
        return genrePage.hasContent() ? genrePage.getContent() : Collections.emptyList();
    }

    public void deleteGenreById(int id) {
        genreRepository.deleteById(id);
    }
}
