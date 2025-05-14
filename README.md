# Music Recommendation API

---

## Outline
An application that gives a user a list of songs that correspond to specified attributes, such as tempo, danceability and genre.

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

![Alt text](readme_files/API-ERD.png "ERD")

The database uses data from [Spotify track dataset by MaharshiPandya on kaggle](https://www.kaggle.com/datasets/maharshipandya/-spotify-tracks-dataset)

---

## Frontend

### REACT / CSS

---

## Backend

### Java / Spring Boot


### SQL

In the repositories, the database is called with a set of sorted queries, finding a list of songs by:
  - genre (id)
  - artist (id)
  - a minimum and maximum danceability range
  - a minimum and maximum tempo range
