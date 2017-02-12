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
import ladsoft.com.popularmoviesapp.databinding.ListItemMovieReviewBinding;
import ladsoft.com.popularmoviesapp.model.MovieReview;

public class MovieReviewsAdapter<T extends MovieReview> extends RecyclerView.Adapter<MovieReviewsAdapter.Viewholder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<T> dataSource;
    private Callback<T> callback;

    public MovieReviewsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.context = inflater.getContext();
        this.dataSource = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public MovieReviewsAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemMovieReviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_movie_review, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapter.Viewholder holder, int position) {
        ListItemMovieReviewBinding binding = holder.getBinding();
        T review = dataSource.get(position);
        binding.author.setText(review.getAuthor());
        binding.content.setText(review.getContent());
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
        private ListItemMovieReviewBinding binding;

        public Viewholder(ListItemMovieReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public ListItemMovieReviewBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View view) {
            if (callback != null) {
                callback.onItemClick(dataSource.get(getAdapterPosition()));
            }
        }
    }

    public interface Callback<T> {
        void onItemClick(T review);
    }
}
