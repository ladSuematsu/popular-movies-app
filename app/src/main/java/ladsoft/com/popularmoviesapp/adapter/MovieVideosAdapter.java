package ladsoft.com.popularmoviesapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ListItemMovieVideoBinding;
import ladsoft.com.popularmoviesapp.model.MovieVideo;

public class MovieVideosAdapter<T extends MovieVideo> extends RecyclerView.Adapter<MovieVideosAdapter.Viewholder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<T> dataSource;
    private Callback<T> callback;

    public MovieVideosAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.context = inflater.getContext();
        this.dataSource = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public MovieVideosAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemMovieVideoBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_movie_video, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapter.Viewholder holder, int position) {
        ListItemMovieVideoBinding binding = holder.getBinding();
        T video = dataSource.get(position);
        holder.setData(video);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setDataSource(@NonNull List<T> dataSource) {
        this.dataSource = dataSource;
        this.notifyDataSetChanged();
    }

    public void clearData() {
        this.dataSource.clear();
        this.notifyDataSetChanged();
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemMovieVideoBinding binding;

        public Viewholder(ListItemMovieVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public ListItemMovieVideoBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View view) {
            if (callback != null) {
                callback.onItemClick(dataSource.get(getAdapterPosition()));
            }
        }

        public void setData(T data) {
            binding.description.setText(data.getName());

        }
    }

    public interface Callback<T> {
        void onItemClick(T video);
    }
}
