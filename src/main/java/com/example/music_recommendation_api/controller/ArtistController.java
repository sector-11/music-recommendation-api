package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Artist;
import com.example.music_recommendation_api.secrets.Secrets;
import com.example.music_recommendation_api.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(artistService.getArtistById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Artist> postArtist(@RequestParam(name = "auth") String authorization,
                                             @RequestBody Artist artist) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            return new ResponseEntity<>(artistService.addArtist(artist), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArtistById(@RequestParam(name = "auth") String authorization,
                                                   @PathVariable(name = "id") int id) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            artistService.deleteArtistById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
