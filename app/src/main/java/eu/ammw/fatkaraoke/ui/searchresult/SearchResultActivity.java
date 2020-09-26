package eu.ammw.fatkaraoke.ui.searchresult;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import eu.ammw.fatkaraoke.R;
import eu.ammw.fatkaraoke.common.Callback;
import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.model.Song;

import static eu.ammw.fatkaraoke.common.Extra.QUERY;

public class SearchResultActivity extends AppCompatActivity implements HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    @Inject
    SearchResultViewModel viewModel;
    @Inject
    SongRepository songRepository;
    @Inject
    SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        String query = getIntent().getStringExtra(QUERY);
        songRepository.searchSongs(query, new Callback<ArrayList<Song>>() {
            @Override
            public void onComplete(final ArrayList<Song> result) {
                viewModel.updateResult(result);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        searchResultFragment.notifyDataChanged();
                    }
                });
            }
        });

        setContentView(R.layout.search_result_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, searchResultFragment)
                    .commitNow();
        }
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
