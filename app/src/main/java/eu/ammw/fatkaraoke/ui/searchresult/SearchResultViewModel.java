package eu.ammw.fatkaraoke.ui.searchresult;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import eu.ammw.fatkaraoke.R;
import eu.ammw.fatkaraoke.model.Song;

public class SearchResultViewModel extends ViewModel {
    private final ArrayList<Song> songs = new ArrayList<>();

    public int getSize() {
        return songs.size();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void updateResult(List<Song> result) {
        songs.clear();
        songs.addAll(result);
    }
}
