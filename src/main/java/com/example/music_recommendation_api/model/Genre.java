package com.example.music_recommendation_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    List<Song> songs = new ArrayList<>();
}
