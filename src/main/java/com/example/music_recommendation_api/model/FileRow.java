package com.example.music_recommendation_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileRow {
    private final int lineNumber;
    private String trackId;
    private String artists;
    private String albumName;
    private String trackName;
    private int popularity;
    private int durationMs;
    private boolean explicit;
    private float danceability;
    private float energy;
    private int key;
    private float loudness;
    private boolean mode;
    private float speechiness;
    private float acousticness;
    private double instrumentalness;
    private float liveness;
    private float valence;
    private float tempo;
    private int timeSignature;
    private String trackGenre;

    public FileRow(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return lineNumber + " {" +
                "trackId='" + trackId + '\'' +
                ", artists='" + artists + '\'' +
                ", albumName='" + albumName + '\'' +
                ", trackName='" + trackName + '\'' +
                ", popularity=" + popularity +
                ", durationMs=" + durationMs +
                ", explicit=" + explicit +
                ", danceability=" + danceability +
                ", energy=" + energy +
                ", key=" + key +
                ", loudness=" + loudness +
                ", mode=" + mode +
                ", speechiness=" + speechiness +
                ", acousticness=" + acousticness +
                ", instrumentalness=" + instrumentalness +
                ", liveness=" + liveness +
                ", valence=" + valence +
                ", tempo=" + tempo +
                ", timeSignature=" + timeSignature +
                ", trackGenre='" + trackGenre + '\'' +
                '}';
    }
}
