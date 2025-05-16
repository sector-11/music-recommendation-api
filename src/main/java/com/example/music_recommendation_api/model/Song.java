package com.example.music_recommendation_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String spotifyId;
    @Column(columnDefinition = "VARCHAR(1020)")
    private String track_name;
    private int duration;
    @Column(columnDefinition = "TINYINT")
    private boolean explicit;
    private int popularity;
    private float tempo;
    private float energy;
    private float danceability;
    private float loudness;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "song_artists",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    List<Artist> artists = new ArrayList<>();

    @ManyToMany(mappedBy = "songs", cascade=CascadeType.ALL)
    List<Album> albums = new ArrayList<>();

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}

