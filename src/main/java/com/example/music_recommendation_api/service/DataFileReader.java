package com.example.music_recommendation_api.service;

import com.example.music_recommendation_api.model.FileRow;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DataFileReader {
    private final Pattern regex;
    private final String filepath;
    private BufferedReader reader;

    public DataFileReader(String filepath) {
        this.regex = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        this.reader = null;
        this.filepath = filepath;
    }


    public List<FileRow> readFile() {
        List<FileRow> output = new ArrayList<>();
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(filepath));
            reader.readLine(); //skip first line

            while ((line = reader.readLine()) != null) {

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
}
