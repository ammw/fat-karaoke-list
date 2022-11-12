package eu.ammw.fatkaraoke.data;

import static java.util.Optional.ofNullable;

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

    public void searchSongs(final String title, final String artist, final Callback<List<Song>> callback) {
        final String titleQuery = prepareQuery(title);
        final String artistQuery = prepareQuery(artist);
        executorService.execute(() -> {
            List<Song> result;
            if (titleQuery.equals("%") && artistQuery.equals("%")) {
                result = getAllSongs();
            } else {
                result = getSongs(titleQuery, artistQuery);
            }
            Log.i(TAG, "Found songs: " + result.size());
            callback.onComplete(result);
        });
    }

    private List<Song> getAllSongs() {
        Log.i(TAG, "Getting all songs from database");
        return database.songDao().getAll();
    }

    private List<Song> getSongs(final String title, final String artist) {
        Log.i(TAG, "Searching for title: `" + title + "`, artist: `" + artist + "`");
        return database.songDao().find(title, artist);
    }

    private String prepareQuery(String input) {
        return ofNullable(input)
                .map(String::trim)
                .filter(q -> !q.isEmpty())
                .map(q -> "%" + q + "%")
                .orElse("%");
    }

    public void updateSongs(Song... songs) {
        database.runInTransaction(() -> {
            database.songDao().deleteAll();
            database.songDao().insertAll(songs);
        });
    }
}
