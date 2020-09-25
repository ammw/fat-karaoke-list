package eu.ammw.fatkaraoke.model;

import java.util.Objects;
import java.util.UUID;

public class Song {
    private static final String FORMAT = "\"%s\" by %s";

    private final UUID id;
    private final String title;
    private final String artist;

    public Song(UUID id, String title, String artist) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.title = title;
        this.artist = artist;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, title, artist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id) && Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist);
    }
}
