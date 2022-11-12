package eu.ammw.fatkaraoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.page.UpdateWorker;
import eu.ammw.fatkaraoke.ui.searchresult.EmptyResultFragment;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultFragment;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultViewModel;

public class MainActivity extends AppCompatActivity implements HasAndroidInjector {
    private static final String TAG = "FKA";
    private static final int PERMISSION_ALL = 1;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
    };

    private static final AtomicLong sequence = new AtomicLong();

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.result_container, searchResultFragment)
                    .commitNow();
        }
        ensurePermissions();
        setSearchFieldListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            scheduleUpdate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ensurePermissions() {
        if (!hasPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        if (!hasPermissions(PERMISSIONS)) {
            Log.e(TAG, "No permissions despite requesting!");
        }
    }

    private void setSearchFieldListeners() {
        SearchTextWatcher listener = new SearchTextWatcher();
        EditText searchTitleBox = findViewById(R.id.searchTitleBox);
        searchTitleBox.addTextChangedListener(listener);
        EditText searchArtistBox = findViewById(R.id.searchArtistBox);
        searchArtistBox.addTextChangedListener(listener);

    }

    private void scheduleUpdate() {
        WorkRequest updateRequest = new OneTimeWorkRequest.Builder(UpdateWorker.class).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(updateRequest);
        Log.d(TAG, "Scheduled update");
    }

    private boolean hasPermissions(String... permissions) {
        return Arrays.stream(permissions).allMatch(permission -> checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    private class SearchTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable search) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence text, int start, int before, int count) {
            EditText title = findViewById(R.id.searchTitleBox);
            final String titleText = title.getText().toString();
            EditText artist = findViewById(R.id.searchArtistBox);
            final String artistText = artist.getText().toString();
            long mySequence = sequence.incrementAndGet();
            executorService.execute(() ->
                    songRepository.searchSongs(titleText, artistText, result -> {
                        if (sequence.get() == mySequence) {
                            viewModel.updateResult(result);
                            if (!result.isEmpty()) {
                                runOnUiThread(searchResultFragment::notifyDataChanged);
                            }
                            runOnUiThread(() ->
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.result_container, result.isEmpty() ? emptyResultFragment : searchResultFragment)
                                            .commitNow());
                        } else {
                            Log.d(TAG, "Discarded the results for `" + titleText + "` - `" + artistText + "` (seq#" + mySequence + ") as a newer search has already started (seq#" + sequence.get() + ")");
                        }}));
        }
    }
}
