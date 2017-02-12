package ladsoft.com.popularmoviesapp.adapter;

import android.content.Context;
import android.database.Cursor;
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

public class FavoritesAdapter extends CursorRecyclerViewAdapter<FavoritesAdapter.Viewholder> {

    private final LayoutInflater inflater;
    private final Context context;
    private MovieDiscoveryAdapter.Callback<Movie> callback;

    public FavoritesAdapter(LayoutInflater inflater, Cursor cursor) {
        super(inflater.getContext(), cursor);
        this.context = inflater.getContext();
        this.inflater = inflater;
        setHasStableIds(true);
    }

    @Override
    public FavoritesAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemMoviePosterBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_movie_poster, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.Viewholder viewHolder, Cursor cursor) {
        viewHolder.setData(cursor);
    }

//
//    public void setDataSource(@NonNull List<T> dataSource) {
//        this.dataSource = dataSource;
//        this.notifyDataSetChanged();
//    }
//
//    public void clearData() {
//        this.dataSource.clear();
//        this.notifyDataSetChanged();
//    }

    public void setCallback(MovieDiscoveryAdapter.Callback<Movie> callback) {
        this.callback = callback;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemMoviePosterBinding binding;
        private Movie movie;

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
            callback.onItemClick(binding.poster, movie);
        }

        public void setData(Cursor data) {
            movie = new Movie(data);

            Glide.with(context)
                    .load(movie.getPosterPath())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.poster);
        }
    }
}
