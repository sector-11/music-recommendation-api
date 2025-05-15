package com.example.music_recommendation_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    List<Song> songs = new ArrayList<>();
}
