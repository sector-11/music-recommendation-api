package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.Album;
import com.example.music_recommendation_api.repository.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<Album> getAllAlbums(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Album> albumPage = albumRepository.findAll(pageable);
        return albumPage.hasContent() ? albumPage.getContent() : Collections.emptyList();
    }

    public void deleteAlbumById(int id) {
        albumRepository.deleteById(id);
    }
}
