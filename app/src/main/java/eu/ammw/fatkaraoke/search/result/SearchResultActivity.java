package eu.ammw.fatkaraoke.search.result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import eu.ammw.fatkaraoke.MainActivity;
import eu.ammw.fatkaraoke.R;

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
        String message = intent.getStringExtra(MainActivity.QUERY);
        Log.w("QUERY", message);
        // TODO display results in table
    }
}
