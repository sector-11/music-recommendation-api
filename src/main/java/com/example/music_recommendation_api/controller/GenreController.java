package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.secrets.Secrets;
import com.example.music_recommendation_api.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> postGenre(@RequestParam(name = "auth") String authorization,
                                           @RequestBody Genre genre) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            return new ResponseEntity<>(genreService.addGenre(genre), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGenreById(@RequestParam(name = "auth") String authorization,
                                                      @PathVariable(name = "id") int id) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            genreService.deleteGenreById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}