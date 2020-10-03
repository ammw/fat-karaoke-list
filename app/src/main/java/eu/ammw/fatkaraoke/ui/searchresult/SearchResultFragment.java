package eu.ammw.fatkaraoke.ui.searchresult;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import eu.ammw.fatkaraoke.R;

/**
 * A fragment representing a list of Items.
 */
public class SearchResultFragment extends DaggerFragment {

    @Inject
    SearchResultViewModel viewModel;

    @Inject
    SearchResultRecyclerViewAdapter viewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @Inject
    public SearchResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(viewAdapter);
        }
        return view;
    }

    public void notifyDataChanged() {
        viewAdapter.notifyDataSetChanged();
    }
}
