package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Artist;
import com.example.music_recommendation_api.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artistList = artistService.getAllArtists();
        return artistList.isEmpty()
                ? new ResponseEntity<>(artistList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(artistList, HttpStatus.OK);
    }
}
