package ladsoft.com.popularmoviesapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.Calendar;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.databinding.FragmentMovieDetailsBinding;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.presenter.MovieDetailPresenterFactory;
import ladsoft.com.popularmoviesapp.presenter.MovieDetailsPresenter;
import ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryPresenter;
import ladsoft.com.popularmoviesapp.util.DateUtils;

public class MovieDetailsFragment extends Fragment implements MovieDetailsPresenter.Callback<Movie> {

    private static final String ARG_MOVIE = "arg_movie";
    private FragmentMovieDetailsBinding binding;
    private MovieDetailsPresenter<Movie> presenter;

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

        presenter = MovieDetailPresenterFactory.create(this);
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

        binding.toolbar.inflateMenu(R.menu.movie_details_menu);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.movie_details_menu_favorite) {
                    presenter.setFavorite();
                    return true;
                }
                return false;
            }
        });

        binding.appBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullSizePoster();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        Movie movie = null;
        if(args != null) {
            movie = args.getParcelable(ARG_MOVIE);
        }

        presenter.loadData(movie);
    }

    private void showFullSizePoster() {
        ImageShowDialogFragment posterDialog = ImageShowDialogFragment.newInstance(presenter.getPosterPath());
        posterDialog.show(getFragmentManager(), null);
    }

    @Override
    public void onDataLoaded(Movie movie) {
        Calendar calendar = DateUtils.getCalendar(movie.getReleaseDate(), getString(R.string.movie_details_date_format));
        DecimalFormat ratingFormat = new DecimalFormat(getString(R.string.movie_details_user_rating_format));

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
    }

    @Override
    public void onFavoriteSuccessful() {

    }

    @Override
    public void onError() {

    }
}
