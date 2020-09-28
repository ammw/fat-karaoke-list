package eu.ammw.fatkaraoke.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Song {
    private static final String FORMAT = "\"%s\" by %s";

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo
    private final String title;
    @ColumnInfo
    private final String artist;

    public Song(Long id, String title, String artist) {
        this(title, artist);
        this.id = id;
    }

    @Ignore
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public void setId(Long id) {
        this.id = id;
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
