package eu.ammw.fatkaraoke.ui.searchresult;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import eu.ammw.fatkaraoke.R;

/**
 * A fragment representing a list of Items.
 */
public class SearchResultFragment extends DaggerFragment {

    private static final int COLUMN_COUNT = 1;

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

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(new Bundle());
        return fragment;
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
            if (COLUMN_COUNT <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, COLUMN_COUNT));
            }
            recyclerView.setAdapter(viewAdapter);
        }
        return view;
    }

    public void notifyDataChanged() {
        viewAdapter.notifyDataSetChanged();
    }
}
