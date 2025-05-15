package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<Song> getSongsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songPage = songRepository.findAll(pageable);
        return songPage.hasContent() ? songPage.getContent() : Collections.emptyList();
    }

    public Song getSongById(Integer songId){
        return songRepository.findById(songId).orElseThrow();
    }

    public ArrayList<Song> getSongByGenre(Integer genre_id){
//        ArrayList<Song> foundSongsByGenre = new ArrayList<>();
//        Iterable<Song> foundSongs = songRepository.findAll();
//        foundSongs.forEach(song -> {foundSongsByGenre.add(song);});
//        return foundSongsByGenre;
        return songRepository.findSongByGenreOrderByTrack_NameAsc(genre_id).orElseThrow();
    }

    public ArrayList<Song> getSongByArtist(Integer artist_id){
//        ArrayList<Song> foundSongsByArtist = new ArrayList<>();
//        Iterable<Song> foundSongs = songRepository.findAll();
//        foundSongs.forEach(song -> {foundSongsByArtist.add(song);});
//        return foundSongsByArtist;
        return songRepository.findSongByArtistOrderByTrack_NameAsc(artist_id).orElseThrow();
    }

    public ArrayList<Song> getSongByDanceability(float minValue, float maxValue){
        return songRepository.findSongByDanceabilityOrderByDanceabilityAsc(
                minValue, maxValue).orElseThrow();
    }

    public ArrayList<Song> getSongByTempo(float minValue, float maxValue){
        return songRepository.findSongByTempoOrderByTempoAsc(
                minValue, maxValue).orElseThrow();
    }



}
