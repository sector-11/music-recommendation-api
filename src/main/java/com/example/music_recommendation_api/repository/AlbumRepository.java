package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository <Album, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT albums.* FROM songs, albums, album_songs " +
                    "WHERE album_songs.song_id = songs.id " +
                    "AND albums.id = album_songs.album_id " +
                    "AND songs.id = :albumId")
    Optional<List<Album>> findAlbumsBySong(@Param("albumId") Integer albumId);
}
