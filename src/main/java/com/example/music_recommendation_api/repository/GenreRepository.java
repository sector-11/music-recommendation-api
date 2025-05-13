package com.example.music_recommendation_api.repository;

import com.example.music_recommendation_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
