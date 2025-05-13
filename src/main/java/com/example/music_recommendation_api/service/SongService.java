package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    // add a song
    public Song addSong(Song song){
        return songRepository.save(song);
    }
    //add an album?

    // add an artist

    // add a genre

    // get all songs
    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    public Song getSongById(Integer songId){
        return songRepository.findById(songId).orElseThrow();
    }

    public ArrayList<Song> getSongByGenre(int genre_id){
        ArrayList<Song> foundSongsByGenre = new ArrayList<>();
        Iterable<Song> foundSongs = songRepository.findAll();
        foundSongs.forEach(song -> {foundSongsByGenre.add(song);});
        return foundSongsByGenre;
    }

    public ArrayList<Song> getSongByArtist(int artist_id){
        ArrayList<Song> foundSongsByArtist = new ArrayList<>();
        Iterable<Song> foundSongs = songRepository.findAll();
        foundSongs.forEach(song -> {foundSongsByArtist.add(song);});
        return foundSongsByArtist;
    }



}
