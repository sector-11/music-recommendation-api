# Music Recommendation API

---

## Outline
An application that gives a user a list of songs that correspond to specified attributes, such as tempo, danceability and genre.
It is also possible to enter a Spotify id, and be given a set of song that match it's "vibe", using pre-existing attributes in a close range.

The application is connected to a database of songs, artists and albums to query the user's specifications and return back a list of similar songs they might be interested in.

This application is made with Java and Spring Boot for the backend database, and HTML, REACT and CSS for the frontend website.

This project was worked in collaboration by:
- [Lizzie](https://github.com/LizzieH97)
- [Lotanna](https://github.com/Tannababy)
- [Jude](https://github.com/sector-11)
- [Megan](https://github.com/Megan-0401)

---

## Database

The database consists of 4 main tables: songs, artists, albums and genres. For the many-to-many relationships, there are 3 connecting tables: song_artists, album_artists and album_songs.

![API Entity Relationship Diagram](readme_files/API-ERD.png "ERD")

The database and the file `src/main/resources/testdata.csv` contain information from Spotify Tracks Dataset, which is made available
[here](https://www.kaggle.com/datasets/maharshipandya/-spotify-tracks-dataset) under the [Open Database License (ODbL)](https://opendatacommons.org/licenses/odbl/1-0/). The text of this license can also be found at `src/main/resources/odbl-10.txt`.

---

## Frontend

### REACT / CSS

[The frontend](https://github.com/LizzieH97/vibe-match-front-end) consists of the website the user can access.

<img src="readme_files/main_page.png" alt="main_page" width="750"/>

On the sidebar, the user is presented with several options of how they would like to find songs:
- Genre
- Danceability
- Tempo
- Popularity

For genre, a list of genres present in the database will be fetched and displayed in a set of buttons.

For the other choices, the page will change to present the user with certain ranges of their chosen attribute. The page will then give the user a list of songs between the chosen range.

Alternatively on the main page, the user can input a Spotify id into the search bar, and receive back a list of songs with a similar 'vibe'.
This consists of looking at the song's attribute values, and presenting a list of songs that fall into a close range.

The site uses API calls to fetch song data per request, using the endpoints specified in the backend to access the data in JSON format.

    export async function fetchAllSongs(): Promise<Song[]> {
        const res = await fetch("http://localhost:8080/api/songs");
        if (!res.ok) throw new Error("Failed to fetch songs");
        return res.json() as Promise<Song[]>;
    }

This fetches all the songs in the database.

---

## Backend

### Installation Guide

-   Ensure you have Java 21 installed. (Developed with Amazon Corretto 21.0.6)
-   Clone [this](https://github.com/sector-11/music-recommendation-api.git) repository.
-   Create an application-rds.properties file which points to your MySQL repository, or use the default in-memory database (NOT RECOMMENDED)
-   Create a 'secrets' package and 'Secrets' class, which contains a String field called "admin" and a 'getAdmin()' method - Note that this string is the password for authorizing the use of potentially destructive endpoints
-   Run mvn package to build the program
-   Run program with `java -jar <OUTPUT FROM MVN>`
-   Close program with `CTRL + C`

### Endpoints

Valid types are "artists", "albums", "genres", "songs"
Available on all types:

| Method | Endpoint         | Action                                                 | Notes                                                                                                                          |
| ------ | ---------------- | ------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------ |
| GET    | /api/<TYPE>      | Retrieves all entries of given type from the database  | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the top 10               |
| POST   | /api/<TYPE>      | Posts a given entry to the database                    | Requires relevant object in body of request and the 'auth' parameter to be set to the APIs password for request to be accepted |
| GET    | /api/<TYPE>/{id} | Retrieves an entry with the given id from the database |                                                                                                                                |
| DELETE | /api/<TYPE>/{id} | Deletes an entry with the given id from the database   | Requires 'auth' parameter to be set to the APIs password for request to be accepted                                            |

Available only for songs:

| Method | Endpoint                                          | Action                                                                               | Notes                                                                                                                                                                                   |
| ------ | ------------------------------------------------- | ------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| GET    | /api/songs/byid/{song_id}                         | Retrieves song with given id in database                                             |                                                                                                                                                                                         |
| GET    | /api/songs/byspotifyid/{spotify_id}               | Retrieves song with the given Spotify id from the database                           |                                                                                                                                                                                         |
| GET    | /api/songs/bygenre/{genre_id}                     | Retrieves all songs with genres matching the given id                                | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the first 50 sorted by track name                                                 |
| GET    | /api/songs/byartist/{artist_id}                   | Retrieves all songs with artist matching the given id                                | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the first 50 sorted by track name                                                 |
| GET    | /api/songs/bydanceability/{min_value},{max_value} | Retrieves all songs with danceability values in the given range                      | "Danceability" values were assigned by Spotify and in used dataset and results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the top 50 |
| GET    | /api/songs/bytempo/{min_value},{max_value}        | Retrieves all songs with tempo in the given range                                    | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the top 50                                                                        |
| GET    | /api/songs/bypopularity                           | Retrieves a list of the most popular songs                                           | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the top 10                                                                        |
| GET    | /api/songs/recommend/{spotify_id}                 | Retrieves a list of songs which are similar to the one matching the given Spotify id | Results are paginated and can be moved through with the 'page' and 'size' parameters. By default gets the first 10, if results are available.                                           |



### SQL

In the repositories, the database is called with a set of sorted queries, finding a list of songs by:
  - genre (id)
  - artist (id)
  - a minimum and maximum danceability range
  - a minimum and maximum tempo range
  - popularity, sorted by most popular

This is a snippet of an SQL query in which a list of songs is returned by a single parameter:

    SELECT songs.* FROM songs, artists, song_artists
    WHERE song_artists.song_id = songs.id
    AND song_artists.artist_id = artists.id
    AND artists.id = :artistId

Where :artistId is a stand in for the parameter.

This is a snippet of an SQL query in which a list of songs is returned by two parameters:

    SELECT DISTINCT songs.* FROM songs, artists, song_artists
    WHERE song_artists.song_id = songs.id
    AND song_artists.artist_id = artists.id
    AND songs.danceability > :min AND songs.danceability < :max

Where :min and :max are stand ins for the parameters.

The queries are additionally sorted by a Sort parameter from the org.springframework.data.domain package.
This code is used in the SongService:

    songRepository.findSongByArtist(artist_id, Sort.by("track_name")

This sorts the list by the song names, ascending.

    songRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "popularity"))

This sorts the list by the song popularity, descending.