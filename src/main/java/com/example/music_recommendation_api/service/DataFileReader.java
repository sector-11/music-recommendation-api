package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.*;
import com.example.music_recommendation_api.repository.AlbumRepository;
import com.example.music_recommendation_api.repository.ArtistRepository;
import com.example.music_recommendation_api.repository.GenreRepository;
import com.example.music_recommendation_api.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class DataFileReader {
    private final String FILEPATH = "src/main/resources/testdata.csv";
    private final Pattern regex;
    private BufferedReader reader;
    private final Map<String, Artist> artists = new HashMap<>();
    private final Map<String, Album> albums = new HashMap<>();
    private final Map<String, Genre> genres = new HashMap<>();
    private final List<String> uniqueSongs = new ArrayList<>();

    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final ArtistRepository artistRepository;
    @Autowired
    private final GenreRepository genreRepository;
    @Autowired
    private final SongRepository songRepository;

    public DataFileReader(AlbumRepository albumRepository, ArtistRepository artistRepository, GenreRepository genreRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
        this.regex = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        this.reader = null;
    }


    public List<FileRow> readFile() {
        List<FileRow> output = new ArrayList<>();
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(FILEPATH));
            reader.readLine(); //skip first line

            while ((line = reader.readLine()) != null) {
                output.add(this.readLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return output;
    }

    private FileRow readLine(String line) {
        String[] row = regex.split(line, -1);

        FileRow data = new FileRow(Integer.parseInt(row[0]));
        data.setTrackId(row[1]);;
        data.setArtists(row[2]);
        data.setAlbumName(row[3]);
        data.setTrackName(row[4]);
        data.setPopularity(Integer.parseInt(row[5]));
        data.setDurationMs(Integer.parseInt(row[6]));
        data.setExplicit(Boolean.parseBoolean(row[7]));
        data.setDanceability(Float.parseFloat(row[8]));
        data.setEnergy(Float.parseFloat(row[9]));
        data.setKey(Integer.parseInt(row[10]));
        data.setLoudness(Float.parseFloat(row[11]));
        data.setMode(row[12].equals("1"));
        data.setSpeechiness(Float.parseFloat(row[13]));
        data.setAcousticness(Float.parseFloat(row[14]));
        data.setInstrumentalness(Double.parseDouble(row[15]));
        data.setLiveness(Float.parseFloat(row[16]));
        data.setValence(Float.parseFloat(row[17]));
        data.setTempo(Float.parseFloat(row[18]));
        data.setTimeSignature(Integer.parseInt(row[19]));
        data.setTrackGenre(row[20]);

        return data;
    }

    private Song parseSong(FileRow row) {
        Song song = new Song();

        song.setSpotifyId(row.getTrackId());
        song.setTrack_name(row.getTrackName());
        song.setDuration(row.getDurationMs());
        song.setExplicit(row.isExplicit());
        song.setPopularity(row.getPopularity());
        song.setTempo(row.getTempo());
        song.setEnergy(row.getEnergy());
        song.setDanceability(row.getDanceability());
        song.setLoudness(row.getLoudness());

        return song;
    }

    public void parseAll(List<FileRow> rows) {
        List<Album> completeAlbums = new ArrayList<>();
        List<Artist> completeArtists = new ArrayList<>();
        List<Genre> completeGenres = new ArrayList<>();
        List<Song> completeSongs = new ArrayList<>();
        List<Song> incompleteSongs = new ArrayList<>();

        for (FileRow row : rows) {
            Song song = this.parseSong(row);
            String songString = song.getTrack_name() + " by " + song.getArtists();

            if (uniqueSongs.contains(songString)) {
                continue;
            } else {
                uniqueSongs.add(songString);
            }

            incompleteSongs.add(song);
            song = copySong(song);

            String[] songArtists = row.getArtists().split(";");

            if(!albums.containsKey(row.getAlbumName())) {
                albums.put(row.getAlbumName(), new Album());
                albums.get(row.getAlbumName()).setName(row.getAlbumName());
            }

            Album album = this.copyAlbum(albums.get(row.getAlbumName()));

            album.getSongs().add(song);
            song.getAlbums().add(album);

            for (String artistName : songArtists) {
                if (!artists.containsKey(artistName)) {
                    artists.put(artistName, new Artist());
                    artists.get(artistName).setName(artistName);
                }

                Artist artist = copyArtist(artists.get(artistName));
                artist.getSongs().add(song);
                song.getArtists().add(artist);
                album.getArtists().add(artist);
                completeArtists.add(artist);
            }

            if(!genres.containsKey(row.getTrackGenre())) {
                genres.put(row.getTrackGenre(), new Genre());
                genres.get(row.getTrackGenre()).setGenre(row.getTrackGenre());
            }

            Genre genre = copyGenre(genres.get(row.getTrackGenre()));
            genre.setGenre(row.getTrackGenre());
            genre.getSongs().add(song);
            song.setGenre(genre);

            completeSongs.add(song);
            completeAlbums.add(album);
            completeGenres.add(genre);
        }

        System.out.println("START UPLOAD");

        songRepository.saveAll(incompleteSongs);
        albumRepository.saveAll(albums.values());
        artistRepository.saveAll(artists.values());
        genreRepository.saveAll(genres.values());

        songRepository.saveAll(completeSongs);
        albumRepository.saveAll(completeAlbums);
        artistRepository.saveAll(completeArtists);
        genreRepository.saveAll(completeGenres);

        System.out.println("UPLOAD COMPLETE");
    }

    private Album copyAlbum(Album in) {
        Album out = new Album();
        out.setName(in.getName());
        out.setId(in.getId());
        out.setArtists(new ArrayList<>());
        out.setSongs(new ArrayList<>());
        return out;
    }

    private Artist copyArtist(Artist in) {
        Artist out = new Artist();
        out.setName(in.getName());
        out.setId(in.getId());
        out.setAlbums(new ArrayList<>());
        out.setSongs(new ArrayList<>());
        return out;
    }

    private Genre copyGenre(Genre in) {
        Genre out = new Genre();
        out.setGenre(in.getGenre());
        out.setId(in.getId());
        out.setSongs(new ArrayList<>());
        return out;
    }

    private Song copySong(Song in) {
        Song out = new Song();
        out.setId(in.getId());
        out.setSpotifyId(in.getSpotifyId());
        out.setTrack_name(in.getTrack_name());
        out.setDuration(in.getDuration());
        out.setExplicit(in.isExplicit());
        out.setPopularity(in.getPopularity());
        out.setTempo(in.getTempo());
        out.setEnergy(in.getEnergy());
        out.setDanceability(in.getDanceability());
        out.setLoudness(in.getLoudness());
        out.setArtists(new ArrayList<>());
        out.setAlbums(new ArrayList<>());
        out.setGenre(in.getGenre());
        return out;
    }
}
