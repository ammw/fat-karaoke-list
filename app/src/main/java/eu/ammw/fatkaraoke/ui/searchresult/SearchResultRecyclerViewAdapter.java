package eu.ammw.fatkaraoke.ui.searchresult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import eu.ammw.fatkaraoke.R;
import eu.ammw.fatkaraoke.model.Song;

/**
 * {@link RecyclerView.Adapter} that can display a {@link eu.ammw.fatkaraoke.model.Song}.
 */
public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder> {

    private final SearchResultViewModel viewModel;

    @Inject
    public SearchResultRecyclerViewAdapter(SearchResultViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.song = viewModel.getSongs().get(position);
        holder.titleView.setText(holder.song.getTitle());
        holder.artistView.setText(holder.song.getArtist());
    }

    @Override
    public int getItemCount() {
        return viewModel.getSongs().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView titleView;
        public final TextView artistView;
        public Song song;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.item_number);
            artistView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "|" + artistView.getText() + "'";
        }
    }
}
