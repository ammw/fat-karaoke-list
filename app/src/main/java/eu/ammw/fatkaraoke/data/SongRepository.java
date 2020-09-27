package eu.ammw.fatkaraoke.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import eu.ammw.fatkaraoke.common.Callback;
import eu.ammw.fatkaraoke.db.SongDatabase;
import eu.ammw.fatkaraoke.model.Song;

public class SongRepository {
    private static final String TAG = "SNGRPO";
    private final ExecutorService executorService;
    private final SongDatabase database;

    @Inject
    public SongRepository(ExecutorService executorService, SongDatabase database) {
        this.executorService = executorService;
        this.database = database;
    }

    public void searchSongs(final String query, final Callback<ArrayList<Song>> callback) {
        executorService.execute(() -> {
            ArrayList<Song> result = getSongs(query);
            callback.onComplete(result);
        });
    }

    private ArrayList<Song> getSongs(String query) {
        Log.i(TAG, "Searching for " + query);
        return new ArrayList<>(database.userDao().find("%" + query + "%"));
    }
}
