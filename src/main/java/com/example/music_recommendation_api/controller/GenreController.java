package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genreList = genreService.getAllGenres();
        return genreList.isEmpty()
                ? new ResponseEntity<>(genreList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(genreList, HttpStatus.OK);
    }
}