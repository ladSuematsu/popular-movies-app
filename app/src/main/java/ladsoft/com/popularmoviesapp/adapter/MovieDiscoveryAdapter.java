package ladsoft.com.popularmoviesapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ListItemMoviePosterBinding;

public class MovieDiscoveryAdapter<T extends Object> extends RecyclerView.Adapter<MovieDiscoveryAdapter.Viewholder> {

    private final LayoutInflater inflater;
    private List<T> dataSource;

    public MovieDiscoveryAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.dataSource = new ArrayList<>();
    }

    @Override
    public MovieDiscoveryAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemMoviePosterBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_movie_poster, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(MovieDiscoveryAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setDataSource(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ListItemMoviePosterBinding binding;

        public Viewholder(ListItemMoviePosterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
