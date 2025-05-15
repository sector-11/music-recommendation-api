package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.service.GenreService;
import com.example.music_recommendation_api.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    public final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }



    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}