package com.example.music_recommendation_api.controller;


import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    public final SongService songService;

    public SongController(SongService songService){
        this.songService = songService;
    }


    @PostMapping
    public ResponseEntity<Song> addSong(Song song){
        return new ResponseEntity<>(songService.addSong(song), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songList = songService.getAllSongs();
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/byid/{song_id}")
    public ResponseEntity<Song> getSongById(@PathVariable Integer song_id){
        return new ResponseEntity<>(songService.getSongById(song_id), HttpStatus.OK);
    }

    @GetMapping("/byspotifyid/{spotify_id}")
    public ResponseEntity<Song> getSongById(@PathVariable String spotify_id){
        return new ResponseEntity<>(songService.getSongBySpotifyId(spotify_id), HttpStatus.OK);
    }

    @GetMapping("/bygenre/{genre_id}")
    public ResponseEntity<ArrayList<Song>> getSongByGenre(@PathVariable Integer genre_id){
        ArrayList<Song> songList = songService.getSongByGenre(genre_id);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/byartist/{artist_id}")
    public ResponseEntity<ArrayList<Song>> getSongByArtist(@PathVariable Integer artist_id){
        ArrayList<Song> songList = songService.getSongByArtist(artist_id);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/bydanceability/{minValue},{maxValue}")
    public ResponseEntity<ArrayList<Song>> getSongByDanceability(@PathVariable Float minValue, @PathVariable Float maxValue){
        ArrayList<Song> songList = songService.getSongByDanceability(minValue, maxValue);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/bytempo/{minValue},{maxValue}")
    public ResponseEntity<ArrayList<Song>> getSongByTempo(@PathVariable Float minValue, @PathVariable Float maxValue){
        ArrayList<Song> songList = songService.getSongByTempo(minValue, maxValue);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/bypopularity")
    public ResponseEntity<List<Song>> getSongByPopularity(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                          @RequestParam(name = "size", defaultValue = "10", required = false) int size){
        List<Song> songList = songService.getSongByPopularity(page, size);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }

    @GetMapping("/recommend/{spotifyID}")
    public ResponseEntity<List<Song>> getRecommended(@PathVariable(name = "spotifyID") String spotifyId,
                                     @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                     @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        List<Song> songList = songService.getSimilarSongs(spotifyId, page, size);
        return songList.isEmpty()
                ? new ResponseEntity<>(songList, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(songList, HttpStatus.OK);
    }
}
