package eu.ammw.fatkaraoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.ui.searchresult.EmptyResultFragment;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultFragment;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultViewModel;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {
    private static final int PERMISSION_ALL = 1;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
    };

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    @Inject
    SearchResultViewModel viewModel;
    @Inject
    SongRepository songRepository;
    @Inject
    SearchResultFragment searchResultFragment;
    @Inject
    EmptyResultFragment emptyResultFragment;
    @Inject
    ExecutorService executorService;

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
        ensurePermissions();
    }

    /**
     * Called when the user taps the Search button
     */
    public void runSearch(View view) {
        EditText editText = findViewById(R.id.searchBox);
        final String query = editText.getText().toString();
        executorService.execute(() ->
                songRepository.searchSongs(query, result -> {
                    viewModel.updateResult(result);
                    if (!result.isEmpty()) {
                        runOnUiThread(searchResultFragment::notifyDataChanged);
                    }
                    runOnUiThread(() ->
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.result_container, result.isEmpty() ? emptyResultFragment : searchResultFragment)
                                    .commitNow());
                }));
    }

    private void ensurePermissions() {
        if (!hasPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        if (!hasPermissions(PERMISSIONS)) {
            Log.e("FKA", "PANIC!!!");
        }
    }

    private boolean hasPermissions(String... permissions) {
        return Arrays.stream(permissions).allMatch(permission -> checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
