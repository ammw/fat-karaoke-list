package eu.ammw.fatkaraoke.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import eu.ammw.fatkaraoke.common.Callback;
import eu.ammw.fatkaraoke.dummy.DummyContent;
import eu.ammw.fatkaraoke.model.Song;

public class SongRepository {
    private static final String TAG = "SNGRPO";
    private final ExecutorService executorService;

    @Inject
    public SongRepository(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void searchSongs(final String query, final Callback<ArrayList<Song>> callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Song> result = getSongs(query);
                callback.onComplete(result);
            }
        });
    }

    private ArrayList<Song> getSongs(String query) {
        // TODO
        Log.i(TAG, "Searching for " + query);
        try {
            Thread.sleep(500);
            return DummyContent.ITEMS;
        } catch (InterruptedException e) {
            Log.e(TAG, "boom!", e);
            return new ArrayList<>();
        }
    }
}
