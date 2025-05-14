package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Artist;
import com.example.music_recommendation_api.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist getArtistById(int id) {
        return artistRepository.findById(id).orElseThrow();
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
}
