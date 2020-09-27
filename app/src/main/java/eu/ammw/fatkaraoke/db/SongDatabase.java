package eu.ammw.fatkaraoke.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import eu.ammw.fatkaraoke.db.dao.SongDao;
import eu.ammw.fatkaraoke.model.Song;

@Database(entities = {Song.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao userDao();
}
