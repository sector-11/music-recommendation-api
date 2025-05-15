package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Artist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT artists.* FROM songs, artists, song_artists " +
                    "WHERE song_artists.song_id = songs.id " +
                    "AND song_artists.artist_id = artists.id " +
                    "AND songs.id = :songId")
    Optional<List<Artist>> findSongArtistsBySong(@Param("songId") Integer songId,
                                                 Sort sort);


}
