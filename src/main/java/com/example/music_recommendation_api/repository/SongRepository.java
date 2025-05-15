package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Song;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM songs WHERE genre_id = :genreId")
    Optional<ArrayList<Song>> findSongByGenreOrderByTrack_NameAsc(@Param("genreId") Integer genreId);

    @Query(nativeQuery = true, value = "SELECT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE artists.id = :artistId ")
    Optional<ArrayList<Song>> findSongByArtistOrderByTrack_NameAsc(@Param("artistId") Integer artist_id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE songs.danceability > :min AND songs.danceability < :max")
    Optional<ArrayList<Song>> findSongByDanceabilityOrderByDanceabilityAsc(@Param("min") float minValue,
                                                     @Param("max") float maxValue);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE songs.tempo > :min AND songs.tempo < :max")
    Optional<ArrayList<Song>> findSongByTempoOrderByTempoAsc(@Param("min") float minValue,
                                              @Param("max") float maxValue);
}