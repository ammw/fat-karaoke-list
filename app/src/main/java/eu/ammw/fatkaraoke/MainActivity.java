package eu.ammw.fatkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import dagger.android.AndroidInjection;
import eu.ammw.fatkaraoke.ui.searchresult.SearchResultActivity;

import static eu.ammw.fatkaraoke.common.Extra.QUERY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Search button
     */
    public void runSearch(View view) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        EditText editText = findViewById(R.id.searchBox);
        String message = editText.getText().toString();
        intent.putExtra(QUERY, message);
        startActivity(intent);
    }
}
