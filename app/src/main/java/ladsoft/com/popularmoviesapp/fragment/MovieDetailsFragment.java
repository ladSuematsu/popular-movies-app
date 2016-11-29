package ladsoft.com.popularmoviesapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.FragmentMovieDetailsBinding;
import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDetailsFragment extends Fragment {

    private static final String ARG_MOVIE = "arg_movie";
    private FragmentMovieDetailsBinding binding;
    private Movie movie;

    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if(args != null) {
            movie = args.getParcelable(ARG_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.completeTitle.setText(movie.getTitle());
        binding.synopsis.setText(movie.getOverview());
        binding.userRating.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(this)
                .load(movie.getPosterPath())
                .centerCrop()
                .placeholder(android.R.drawable.sym_def_app_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.appBarImage);
    }
}
