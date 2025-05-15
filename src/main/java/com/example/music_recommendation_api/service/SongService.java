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

    public Song getSongBySpotifyId(String spotifyId){
        return songRepository.findSongBySpotifyId(spotifyId).orElseThrow();
    }

    public ArrayList<Song> getSongByGenre(Integer genre_id){
        return songRepository.findSongByGenre(genre_id, Sort.by("track_name")).orElseThrow();
    }

    public ArrayList<Song> getSongByArtist(Integer artist_id){
        return songRepository.findSongByArtist(artist_id, Sort.by("track_name")).orElseThrow();
    }

    public ArrayList<Song> getSongByDanceability(float minValue, float maxValue){
        return songRepository.findSongByDanceability(
                minValue, maxValue, Sort.by("danceability")).orElseThrow();
    }

    public ArrayList<Song> getSongByTempo(float minValue, float maxValue){
        return songRepository.findSongByTempo(
                minValue, maxValue, Sort.by("tempo")).orElseThrow();
    }

    public List<Song> getSongByPopularity(int page, int size) {
        Page<Song> songPage = songRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "popularity")));
        return songPage.hasContent() ? songPage.getContent() : Collections.emptyList();
    }

    public List<Song> getSimilarSongs(String spotifyId, int page, int size) {
        Song song = songRepository.findSongBySpotifyId(spotifyId).orElseThrow();
        float danceability = song.getDanceability();
        float tempo = song.getTempo();

        List<Song> songs = songRepository.findByDanceabilityBetweenAndTempoBetweenAndGenreOrderByPopularityDesc(
                danceability - 0.1f, danceability + 0.1f,
                tempo - 5f, tempo + 5f,
                song.getGenre()
        ).orElseThrow();

        songs.remove(song);

        return songs;
    }
}
