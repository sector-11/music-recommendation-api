package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository <Album, Integer> {
}
