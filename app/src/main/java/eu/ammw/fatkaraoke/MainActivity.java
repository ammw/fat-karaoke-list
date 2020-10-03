package eu.ammw.fatkaraoke;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultFragment;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultViewModel;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {
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
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.result_container, searchResultFragment)
                    .commitNow();
        }
    }

    /**
     * Called when the user taps the Search button
     */
    public void runSearch(View view) {
        EditText editText = findViewById(R.id.searchBox);
        String query = editText.getText().toString();
        songRepository.searchSongs(query, result -> {
            viewModel.clearList();
            runOnUiThread(searchResultFragment::notifyDataChanged);
            viewModel.updateResult(result);
            runOnUiThread(searchResultFragment::notifyDataChanged);
        });
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
