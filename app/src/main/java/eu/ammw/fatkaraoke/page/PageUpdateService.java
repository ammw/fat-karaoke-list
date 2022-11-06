package eu.ammw.fatkaraoke.page;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import eu.ammw.fatkaraoke.config.Configuration;
import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.model.Song;

public class PageUpdateService {
    private static final String TAG = "FKA UPDSVC";
    private static final String URL_KEY = "faak.url.list.all";

    private final PageDownloadService downloadService;
    private final PageParser parser;
    private final SongRepository repository;

    private String url;

    @Inject
    PageUpdateService(PageDownloadService downloadService, PageParser parser, SongRepository repository, Configuration configuration) {
        this.downloadService = downloadService;
        this.parser = parser;
        this.repository = repository;
        this.url = configuration.getProperty(URL_KEY);
    }

    void setUrl(String url) {
        this.url = url;
    }

    void performUpdate() throws IOException {
        Log.i(TAG, "Start downloading list");
        String page = downloadService.download(url);
        Log.d(TAG, "Bytes received: " + page.length());
        List<Song> songs = parser.parse(page);
        Log.d(TAG, "Found songs: " + songs.size());
        repository.updateSongs(songs.toArray(new Song[0]));
        Log.d(TAG, "Saved to database");
        Log.i(TAG, "Update successful!");
    }
}
