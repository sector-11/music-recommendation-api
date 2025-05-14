package com.example.music_recommendation_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String spotify_id;
    private String track_name;
    private int duration;
    private boolean explicit;
    private int popularity;
    private float tempo;
    private float energy;
    private float danceability;
    private float loudness;

    @ManyToMany
    @JoinTable(
            name = "song_artists",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    List<Artist> artists;

    @ManyToMany(mappedBy = "songs")
    List<Album> albums;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}

