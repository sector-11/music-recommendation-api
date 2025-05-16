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
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "artists", cascade=CascadeType.ALL)
    @JsonIgnore
    List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "artists", cascade=CascadeType.ALL)
    @JsonIgnore
    List<Song> songs = new ArrayList<>();
}
