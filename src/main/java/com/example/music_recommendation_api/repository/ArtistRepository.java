package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
