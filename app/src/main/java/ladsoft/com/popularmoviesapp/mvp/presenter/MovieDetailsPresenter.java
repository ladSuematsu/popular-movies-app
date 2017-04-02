package ladsoft.com.popularmoviesapp.mvp.presenter;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.core.mvp.presenter.MvpPresenter;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp;

import static ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.VIDEO_LINK_PARSE_ERROR;

public class MovieDetailsPresenter extends MvpPresenter<MovieDetailsMvp.View<Movie, MovieReview, MovieVideo>>
        implements MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo>,
        MovieDetailsMvp.Model.ModelCallback<Movie, MovieReview, MovieVideo> {
    private static final String TAG = MovieDetailsPresenter.class.getSimpleName();

    private final MovieDetailsMvp.Model<Movie, MovieReview, MovieVideo> model;
    private Movie movie;
    private List<MovieReview> reviews;
    private List<MovieVideo> videos;

    public MovieDetailsPresenter(MovieDetailsMvp.Model<Movie, MovieReview, MovieVideo> model) {
        this.model = model;
        this.model.attach(this);
        reviews = new ArrayList<>();
        videos = new ArrayList<>();
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
        if(isViewAttached()) {
            getView().onDataLoaded(movie);
        }
    }

    @Override
    public void onReviewsLoaded(List<MovieReview> reviews) {
        this.reviews = reviews;
        if(isViewAttached()) {
            getView().refreshReviews(reviews);
        }
    }

    @Override
    public void onVideosLoaded(List<MovieVideo> videos) {
        this.videos = videos;
        if(isViewAttached()) {
            getView().refreshVideos(videos);
        }
    }

    @Override
    public void onSaved() {
        getView().onFavoriteSuccessful(movie.isFavorite());
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
            getView().onVideoLaunch(videoUri);
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
                if(isViewAttached()) {
                    getView().showVideosLoadError(true);
                }
                return;

            case VIDEO_LINK_PARSE_ERROR:
                if(isViewAttached()) {
                    getView().showSnackbar(R.string.movie_details_error_generic);
                }
                break;

            case REVIEW_DATA_LOAD_ERROR:
                if(isViewAttached()) {
                    getView().showReviewsLoadError(true);
                }
                return;
            case FAVORITE_ERROR:
                movie.setFavorite(!movie.isFavorite());
                if(isViewAttached()) {
                    getView().onFavoriteSuccessful(movie.isFavorite());
                }
                break;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        model.detach();
        super.finalize();
    }
}
