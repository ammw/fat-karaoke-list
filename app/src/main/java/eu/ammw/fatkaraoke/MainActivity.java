package eu.ammw.fatkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import eu.ammw.fatkaraoke.search.result.SearchResultActivity;

public class MainActivity extends AppCompatActivity {

    public static final String QUERY = "eu.ammw.fatkaraoke.QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Search button
     */
    public void runSearch(View view) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        EditText editText = (EditText) findViewById(R.id.searchBox);
        String message = editText.getText().toString();
        intent.putExtra(QUERY, message);
        startActivity(intent);
    }
}
