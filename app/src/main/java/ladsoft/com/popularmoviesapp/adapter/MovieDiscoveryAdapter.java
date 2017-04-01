package ladsoft.com.popularmoviesapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ListItemMoviePosterBinding;
import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDiscoveryAdapter<T extends Movie> extends RecyclerView.Adapter<MovieDiscoveryAdapter.Viewholder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<T> dataSource;
    private Callback<T> callback;

    public MovieDiscoveryAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.context = inflater.getContext();
        this.dataSource = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public MovieDiscoveryAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemMoviePosterBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_movie_poster, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(MovieDiscoveryAdapter.Viewholder holder, int position) {
        ListItemMoviePosterBinding binding = holder.getBinding();

        Movie movie = dataSource.get(position);

        binding.caption.setText(movie.getTitle());

        Glide.with(context)
            .load(movie.getPosterPath())
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.poster);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public long getItemId(int position) { return dataSource.get(position).getId(); }

    public void setDatasource(@NonNull List<T> dataSource) {
        this.dataSource = dataSource;
        this.notifyDataSetChanged();
    }

    public List<T> getDatasource() {
        return this.dataSource;
    }

    public void clearData() {
        this.dataSource.clear();
        this.notifyDataSetChanged();
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemMoviePosterBinding binding;

        public Viewholder(ListItemMoviePosterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.poster.setOnClickListener(this);
        }

        public ListItemMoviePosterBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View view) {
            callback.onItemClick(binding.poster, dataSource.get(getAdapterPosition()));
        }
    }

    public interface Callback<T> {
        void onItemClick(View view, T movie);
    }
}
