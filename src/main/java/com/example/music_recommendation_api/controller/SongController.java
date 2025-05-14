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

    @GetMapping("/byid/{song_id}")
    public Song getSongById(Integer song_id){
        return songService.getSongById(song_id);
    }

    @GetMapping("/bygenre/{genre_id}")
    public ArrayList<Song> getSongByGenre(Integer genre_id){
        return songService.getSongByGenre(genre_id);
    }

    @GetMapping("/byartist/{artist_id}")
    public ArrayList<Song> getSongByArtist(Integer artist_id){
        return songService.getSongByArtist(artist_id);
    }
}
