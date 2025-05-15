package com.example.music_recommendation_api.controller;


import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.service.SongService;
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
    public Song addSong(Song song){
        return songService.addSong(song);
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/byid/{song_id}")
    public Song getSongById(@PathVariable Integer song_id){
        return songService.getSongById(song_id);
    }

    @GetMapping("/bygenre/{genre_id}")
    public ArrayList<Song> getSongByGenre(@PathVariable Integer genre_id){
        return songService.getSongByGenre(genre_id);
    }

    @GetMapping("/byartist/{artist_id}")
    public ArrayList<Song> getSongByArtist(@PathVariable Integer artist_id){
        return songService.getSongByArtist(artist_id);
    }

    @GetMapping("/bydanceability/{minValue},{maxValue}")
    public ArrayList<Song> getSongByDanceability(@PathVariable Float minValue, @PathVariable Float maxValue){
        return songService.getSongByDanceability(minValue, maxValue);
    }

    @GetMapping("/bytempo/{minValue},{maxValue}")
    public ArrayList<Song> getSongByTempo(@PathVariable Float minValue, @PathVariable Float maxValue){
        return songService.getSongByTempo(minValue, maxValue);
    }
}
