package eu.ammw.fatkaraoke.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.ammw.fatkaraoke.model.Song;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song ORDER BY artist, title")
    List<Song> getAll();

    @Query("SELECT * FROM song WHERE title LIKE :title AND artist LIKE :artist ORDER BY artist, title")
    List<Song> find(String title, String artist);

    @Insert
    void insertAll(Song... songs);

    @Delete
    void delete(Song song);

    @Query("DELETE FROM song")
    void deleteAll();
}
