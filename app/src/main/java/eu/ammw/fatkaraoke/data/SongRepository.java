package eu.ammw.fatkaraoke.data;

import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import eu.ammw.fatkaraoke.common.Callback;
import eu.ammw.fatkaraoke.db.SongDatabase;
import eu.ammw.fatkaraoke.model.Song;

public class SongRepository {
    private static final String TAG = "FKA SONGREPO";
    private final ExecutorService executorService;
    private final SongDatabase database;

    @Inject
    public SongRepository(ExecutorService executorService, SongDatabase database) {
        this.executorService = executorService;
        this.database = database;
    }

    public void searchSongs(final String query, final Callback<List<Song>> callback) {
        executorService.execute(() -> {
            List<Song> result;
            if (query == null || query.isEmpty()) {
                result = getAllSongs();
            } else {
                result = getSongs(query);
            }
            Log.i(TAG, "Found songs: " + result.size());
            callback.onComplete(result);
        });
    }

    private List<Song> getAllSongs() {
        Log.i(TAG, "Getting all songs from database");
        return database.songDao().getAll();
    }

    private List<Song> getSongs(String query) {
        Log.i(TAG, "Searching for " + query);
        return database.songDao().find("%" + query + "%");
    }

    public void updateSongs(Song... songs) {
        database.runInTransaction(() -> {
            database.songDao().deleteAll();
            database.songDao().insertAll(songs);
        });
    }
}
