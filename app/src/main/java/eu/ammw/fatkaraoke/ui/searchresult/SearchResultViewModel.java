package eu.ammw.fatkaraoke.ui.searchresult;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.ammw.fatkaraoke.model.Song;

public class SearchResultViewModel extends ViewModel {
    private final ArrayList<Song> songs = new ArrayList<>();

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public void updateResult(List<Song> result) {
        songs.clear();
        songs.addAll(result);
    }
}
