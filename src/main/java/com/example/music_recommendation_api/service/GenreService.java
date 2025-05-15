package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.repository.GenreRepository;
import org.springframework.stereotype.Service;

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

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public void deleteGenreById(int id) {
        genreRepository.deleteById(id);
    }
}
