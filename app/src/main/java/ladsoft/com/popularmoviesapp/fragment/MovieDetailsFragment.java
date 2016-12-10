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

import java.text.DecimalFormat;
import java.util.Calendar;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.FragmentMovieDetailsBinding;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.util.DateUtils;

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

        Calendar calendar = DateUtils.getCalendar(movie.getReleaseDate(), getString(R.string.movie_details_date_format));
        DecimalFormat ratingFormat = new DecimalFormat(getString(R.string.movie_details_user_rating_format));

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.completeTitleAppbar.setText(movie.getTitle());
        binding.toolbar.setTitle(movie.getTitle());
        binding.ratingAppbar.setText(String.format(
                getString(R.string.movie_details_user_rating_label_format),
                ratingFormat.format(movie.getVoteAverage())));
        binding.yearAppbar.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        binding.year.setText(binding.yearAppbar.getText());
        binding.synopsis.setText(movie.getOverview());
        binding.originalTitle.setText(movie.getOriginalTitle());
        binding.originalLanguage.setText(movie.getOriginalLanguage());

        Glide.with(this)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_movie_white)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.appBarImage);

        binding.appBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullSizePoster();
            }
        });
    }

    private void showFullSizePoster() {
        ImageShowDialogFragment posterDialog = ImageShowDialogFragment.newInstance(movie.getPosterPath());
        posterDialog.show(getFragmentManager(), null);
    }
}
