package ladsoft.com.popularmoviesapp.mvp;


import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;

import static ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.VIDEO_LINK_PARSE_ERROR;

public class MovieDetailsPresenter implements MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo> {
    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    private final MovieDetailsMvp.Model<Movie> model;
    private Movie movie;
    private WeakReference<MovieDetailsMvp.View<Movie, MovieReview, MovieVideo>>
            view;
    private List<MovieReview> reviews;
    private List<MovieVideo> videos;

    public MovieDetailsPresenter(MovieDetailsMvp.View<Movie, MovieReview, MovieVideo> view) {
        this.view = new WeakReference<>(view);
        this.model = new MovieDetailsModel(this);
        reviews = new ArrayList<>();
        videos = new ArrayList<>();
    }

    @Override
    public Context getContext() {
        return ((Fragment) view.get()).getContext();
    }

    @Override
    public void loadData(Movie movie) {
        this.movie = movie;
        model.loadDetails(movie);
    }

    @Override
    public void loadVideos() { model.loadVideos(movie.getId()); }

    @Override
    public void loadReviews() { model.loadReviews(movie.getId());}


    @Override
    public void onMovieDetailsLoaded(Movie data) {
        this.movie = data;
        view.get().onDataLoaded(movie);
    }

    @Override
    public void onReviewsLoaded(List<MovieReview> reviews) {
        this.reviews = reviews;
        view.get().refreshReviews(reviews);
    }

    @Override
    public void onVideosLoaded(List<MovieVideo> videos) {
        this.videos = videos;
        view.get().refreshVideos(videos);
    }

    @Override
    public void onSaved() {
        view.get().onFavoriteSuccessful(movie.isFavorite());
    }

    @Override
    public void onMovieVideoSelected(Context context, MovieVideo video) {
        Uri videoUri = null;

        try {
            String linkFormat = context.getString(R.string.youtube_video_link_format);
            String videoLink = String.format(linkFormat, video.getKey());
            videoUri = Uri.parse(videoLink);
        } catch (NullPointerException e){
            Log.e(TAG, "Invalid key value", e);
        }

        if (videoUri != null) {
            view.get().onVideoLaunch(videoUri);
        } else {
            onError(VIDEO_LINK_PARSE_ERROR);
        }
    }

    @Override
    public String getPosterPath() {
        return movie.getPosterPath();
    }

    @Override
    public void setFavorite() {
        movie.setFavorite(!movie.isFavorite());
        model.saveDetails(movie);
    }

    @Override
    public void onError(MovieDetailsMvp.ErrorType errorType) {
        switch(errorType) {
            case VIDEO_DATA_LOAD_ERROR:
                view.get().showVideosLoadError(true);
                 return;

            case VIDEO_LINK_PARSE_ERROR:
                view.get().showSnackbar(R.string.movie_details_error_generic);
                break;

            case REVIEW_DATA_LOAD_ERROR:
                view.get().showReviewsLoadError(true);
                return;
            case FAVORITE_ERROR:
                movie.setFavorite(!movie.isFavorite());
                view.get().onFavoriteSuccessful(movie.isFavorite());
                break;
        }
    }

}
