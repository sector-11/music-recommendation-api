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

    @Query(nativeQuery = true, value = "SELECT songs.* FROM songs " +
            "INNER JOIN genres ON songs.genre_id = genres.id " +
            "WHERE genres.id =:genreId ")
    Optional<ArrayList<Song>> findSongByGenre(@Param("genreId")int genre_id, Sort sort);

    @Query(nativeQuery = true, value = "SELECT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE artists.id =:artistId ")
    Optional<ArrayList<Song>> findSongByArtist(@Param("artistId") int artist_id, Sort sort);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE songs.danceability >:min AND songs.danceability <:max")
    Optional<ArrayList<Song>> findSongByDanceability(@Param("min") float minValue,
                                                     @Param("max") float maxValue,
                                                     Sort sort);

    @Query(nativeQuery = true, value = "SELECT DISTINCT songs.* FROM songs " +
            "INNER JOIN song_artists ON song_artists.song_id = songs.id " +
            "INNER JOIN artists ON song_artists.artist_id = artists.id " +
            "WHERE songs.tempo >:min AND songs.tempo <:max")
    Optional<ArrayList<Song>> findSongByTempo(@Param("min") float minValue,
                                              @Param("max") float maxValue,
                                              Sort sort);
}