package com.example.music_recommendation_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String genre;

    @OneToMany(mappedBy = "genre")
    List<Song> songs = new ArrayList<>();
}
