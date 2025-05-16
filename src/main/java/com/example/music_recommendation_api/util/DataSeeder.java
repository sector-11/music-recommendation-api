package com.example.music_recommendation_api.util;

import com.example.music_recommendation_api.model.FileRow;
import com.example.music_recommendation_api.service.DataFileReader;
import com.example.music_recommendation_api.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder {
    @Autowired
    private final SongService songService;
    @Autowired
    private final DataFileReader reader;


    public DataSeeder(SongService songService, DataFileReader reader) {
        this.songService = songService;
        this.reader = reader;
    }

    @Bean
    public CommandLineRunner checkAndSeed() {
        if (!songService.getSongsPaginated(0, 5).isEmpty()) {
            return args -> {/*do nothing*/};
        }
        List<FileRow> file = reader.readFile();

        return args -> {reader.parseAll(file);};
    }
}
