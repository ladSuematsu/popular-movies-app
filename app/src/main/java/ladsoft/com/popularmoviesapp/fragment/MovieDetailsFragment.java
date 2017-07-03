package ladsoft.com.popularmoviesapp.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.adapter.MovieReviewsAdapter;
import ladsoft.com.popularmoviesapp.adapter.MovieVideosAdapter;
import ladsoft.com.popularmoviesapp.databinding.FragmentMovieDetailsBinding;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;
import ladsoft.com.popularmoviesapp.mvp.MovieDetailPresenterFactory;
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp;
import ladsoft.com.popularmoviesapp.mvp.model.MovieDetailsModel;
import ladsoft.com.popularmoviesapp.util.DateUtils;
import ladsoft.com.popularmoviesapp.util.UiUtils;

public class MovieDetailsFragment extends Fragment implements MovieDetailsMvp.View<Movie, MovieReview, MovieVideo>,
        MovieVideosAdapter.Callback<MovieVideo> {

    private static final String ARG_MOVIE = "arg_movie";
    private FragmentMovieDetailsBinding binding;
    private MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo> presenter;
    private MovieVideosAdapter<MovieVideo> movieVideosAdapter;
    private MovieReviewsAdapter<MovieReview> movieReviewsAdapter;
    private MenuItem menuFavorite;

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

        presenter = MovieDetailPresenterFactory.create(new MovieDetailsModel(getContext().getContentResolver()));
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
        menuFavorite = binding.toolbar.getMenu().findItem(R.id.movie_details_menu_favorite);
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


        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        movieVideosAdapter = new MovieVideosAdapter<>(getLayoutInflater(savedInstanceState));
        movieVideosAdapter.setCallback(this);
        binding.videos.setNestedScrollingEnabled(false);
        binding.videos.addItemDecoration(dividerItemDecoration);
        binding.videos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.videos.setAdapter(movieVideosAdapter);
        binding.videosLoadRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadVideos();
            }
        });


        movieReviewsAdapter = new MovieReviewsAdapter<>(getLayoutInflater(savedInstanceState));
        binding.reviews.setNestedScrollingEnabled(false);
        binding.reviews.addItemDecoration(dividerItemDecoration);
        binding.reviews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.reviews.setAdapter(movieReviewsAdapter);
        binding.reviewsLoadRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadReviews();
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

        presenter.attachView(this);
        presenter.loadData(movie);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
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

        onFavoriteSuccessful(movie.isFavorite());

        presenter.loadVideos();
        presenter.loadReviews();
    }

    @Override
    public void refreshReviews(@NotNull List<? extends MovieReview> reviews) {
        movieReviewsAdapter.setDataSource((List<MovieReview>) reviews);
        showReviewsEmptyMessage(movieReviewsAdapter.getItemCount() < 1);
        showReviewsLoadError(false);

    }

    @Override
    public void refreshVideos(@NotNull List<? extends MovieVideo> videos) {
        movieVideosAdapter.setDataSource((List<MovieVideo>) videos);
        showVideosEmptyMessage(movieVideosAdapter.getItemCount() < 1);
        showVideosLoadError(false);

    }

    @Override
    public void onVideoLaunch(Uri videoUri) {
        startActivity(new Intent(Intent.ACTION_VIEW, videoUri));
    }

    @Override
    public void onFavoriteSuccessful(boolean favorite) {
        menuFavorite.setIcon(favorite ? R.drawable.ic_grade_white : R.drawable.ic_grade_outline_white);
    }

    @Override
    public void showSnackbar(int messageResourceId) {
        UiUtils.showSnackbar(binding.getRoot(), getString(messageResourceId), null, Snackbar.LENGTH_SHORT, null);
    }

    @Override
    public void showVideosEmptyMessage(boolean show) {
        binding.emptyVideos.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showVideosLoadError(boolean show) {
        binding.errorVideos.setVisibility(show ? View.VISIBLE : View.GONE);

        if(show) {
            movieVideosAdapter.clearData();
        }
    }

    @Override
    public void showReviewsEmptyMessage(boolean show) {
        binding.emptyReviews.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showReviewsLoadError(boolean show) {
        binding.errorReviews.setVisibility(show ? View.VISIBLE : View.GONE);

        if(show) {
            movieReviewsAdapter.clearData();
        }
    }

    @Override
    public void onItemClick(MovieVideo video) {
        presenter.onMovieVideoSelected(getContext(), video);
    }

}
