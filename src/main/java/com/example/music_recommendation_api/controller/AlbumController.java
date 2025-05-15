package com.example.music_recommendation_api.controller;

import com.example.music_recommendation_api.model.Album;
import com.example.music_recommendation_api.secrets.Secrets;
import com.example.music_recommendation_api.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    public final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }


    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albumList = albumService.getAllAlbums();
        return albumList.isEmpty()
                ? new ResponseEntity<>(albumList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(albumList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestParam(name = "auth") String authorization,
                                           @RequestBody Album album) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            return new ResponseEntity<>(albumService.addAlbum(album), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAlbumById(@RequestParam(name = "auth") String authorization,
                                                      @PathVariable(name = "id") int id) {
        Secrets secrets = new Secrets();
        if (secrets.getAdmin().equals(authorization)) {
            albumService.deleteAlbumById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
