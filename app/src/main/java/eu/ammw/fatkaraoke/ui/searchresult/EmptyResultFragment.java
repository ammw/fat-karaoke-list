package eu.ammw.fatkaraoke.ui.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import eu.ammw.fatkaraoke.R;

public class EmptyResultFragment extends DaggerFragment {

    @Inject
    public EmptyResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_result_empty, container, false);
    }
}
