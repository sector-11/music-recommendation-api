package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Genre;
import com.example.music_recommendation_api.model.Song;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM songs WHERE genre_id = :genreId")
    Optional<ArrayList<Song>> findSongByGenre(@Param("genreId") Integer genreId, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "SELECT songs.* FROM songs, artists, song_artists " +
            "WHERE song_artists.song_id = songs.id " +
            "AND song_artists.artist_id = artists.id " +
            "AND artists.id = :artistId ")
    Optional<ArrayList<Song>> findSongByArtist(@Param("artistId") Integer artist_id, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs, artists, song_artists " +
            "WHERE song_artists.song_id = songs.id " +
            "AND song_artists.artist_id = artists.id " +
            "AND songs.danceability > :min AND songs.danceability < :max")
    Optional<ArrayList<Song>> findSongByDanceability(@Param("min") float minValue,
                                                     @Param("max") float maxValue,
                                                     PageRequest pageRequest);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs, artists, song_artists " +
            "WHERE song_artists.song_id = songs.id " +
            "AND song_artists.artist_id = artists.id " +
            "AND songs.tempo > :min AND songs.tempo < :max")
    Optional<ArrayList<Song>> findSongByTempo(@Param("min") float minValue,
                                              @Param("max") float maxValue,
                                                             PageRequest pageRequest);
    Optional<Song> findSongBySpotifyId(String spotifyId);

    Optional<ArrayList<Song>> findByDanceabilityBetweenAndTempoBetweenAndGenreOrderByPopularityDesc(float danceabilityMin, float danceabilityMax, float tempoMin, float tempoMax, Genre genre, PageRequest pageRequest);
}