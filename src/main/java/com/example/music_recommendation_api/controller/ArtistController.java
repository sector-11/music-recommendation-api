package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Artist;
import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.service.ArtistService;
import com.example.music_recommendation_api.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    public final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }



    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }
}
