package eu.ammw.fatkaraoke.ui.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import eu.ammw.fatkaraoke.R;

import static eu.ammw.fatkaraoke.Extra.QUERY;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchResultFragment.newInstance())
                    .commitNow();
        }

        Intent intent = getIntent();
        String message = intent.getStringExtra(QUERY);
        Log.w("QUERY", message);
        // TODO display results in table
    }
}
