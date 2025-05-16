package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Album;
import com.example.music_recommendation_api.model.Artist;
import com.example.music_recommendation_api.model.Song;
import com.example.music_recommendation_api.repository.AlbumRepository;
import com.example.music_recommendation_api.repository.ArtistRepository;
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
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongService(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository){
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
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
        Song song = songRepository.findById(songId).orElseThrow();
        song.setArtists(getArtistsOfSong(song.getId()));
        song.setAlbums(getAlbumsOfSong(song.getId()));
        return song;
    }

    public Song getSongBySpotifyId(String spotifyId){
        return songRepository.findSongBySpotifyId(spotifyId).orElseThrow();
    }

    public ArrayList<Song> getSongByGenre(Integer genre_id){
        ArrayList<Song> songList = songRepository.findSongByGenre(genre_id, Sort.by("track_name")).orElseThrow();
        setSongsArtists(songList);
        setSongsAlbums(songList);
        return songList;
    }

    public ArrayList<Song> getSongByArtist(Integer artist_id){
        ArrayList<Song> songList = songRepository.findSongByArtist(artist_id, Sort.by("track_name")).orElseThrow();
        setSongsArtists(songList);
        setSongsAlbums(songList);
        return songList;
    }

    public ArrayList<Song> getSongByDanceability(float minValue, float maxValue){
        ArrayList<Song> songList = songRepository.findSongByDanceability(
                minValue, maxValue, Sort.by("danceability")).orElseThrow();
        setSongsArtists(songList);
        setSongsAlbums(songList);
        return songList;
    }

    public ArrayList<Song> getSongByTempo(float minValue, float maxValue){
        ArrayList<Song> songList = songRepository.findSongByTempo(
                minValue, maxValue, Sort.by("tempo")).orElseThrow();
        setSongsArtists(songList);
        setSongsAlbums(songList);
        return songList;
    }

    public List<Song> getSongByPopularity(int page, int size) {
        Page<Song> songPage = songRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "popularity")));
        songPage.forEach(song -> {
            song.setArtists(getArtistsOfSong(song.getId()));
            song.setAlbums(getAlbumsOfSong(song.getId()));
        });
        return songPage.hasContent() ? songPage.getContent() : Collections.emptyList();
    }

    public ArrayList<Song> setSongsArtists(ArrayList<Song> songList){
        songList.forEach(song -> song.setArtists(getArtistsOfSong(song.getId())));
        return songList;
    }

    public List<Artist> getArtistsOfSong(int songId) {
        return artistRepository.findSongArtistsBySong(songId, Sort.by("artists.name")).orElseThrow();
    }

    public ArrayList<Song> setSongsAlbums(ArrayList<Song> songList){
        songList.forEach(song -> song.setAlbums(getAlbumsOfSong(song.getId())));
        return songList;
    }

    public List<Album> getAlbumsOfSong(int songId) {
        return albumRepository.findAlbumsBySong(songId).orElseThrow();
    }

    public List<Song> getSimilarSongs(String spotifyId, int page, int size) {
        Song song = songRepository.findSongBySpotifyId(spotifyId).orElseThrow();
        float danceability = song.getDanceability();
        float tempo = song.getTempo();

        List<Song> songs = songRepository.findByDanceabilityBetweenAndTempoBetweenAndGenreOrderByPopularityDesc(
                danceability - 0.1f, danceability + 0.1f,
                tempo - 5f, tempo + 5f,
                song.getGenre(),
                PageRequest.of(page, size + 1)
        ).orElseThrow();

        songs.remove(song);

        return songs;
    }

    public void deleteSongById(int id) {
        songRepository.deleteById(id);
    }
}
