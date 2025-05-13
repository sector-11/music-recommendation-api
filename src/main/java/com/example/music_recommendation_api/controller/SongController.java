package com.example.music_recommendation_api.controller;


import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{spotify_id}")
    public Song getSongById(Long spotify_id){
        return songService.getSongById(spotify_id);
    }

    @GetMapping("/{genre_id}")
    public ArrayList<Song> getSongByGenre(int genre_id){
        return songService.getSongByGenre(genre_id);
    }

    @GetMapping("/{artist_id}")
    public ArrayList<Song> getSongByArtist(int artist_id){
        return songService.getSongByArtist(artist_id);
    }
}
