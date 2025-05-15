package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Album;
import com.example.music_recommendation_api.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album addAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album getAlbumById(int id) {
        return albumRepository.findById(id).orElseThrow();
    }

    public List<Album> getAllArtists() {
        return albumRepository.findAll();
    }

    public void deleteAlbumById(int id) {
        albumRepository.deleteById(id);
    }
}
