package ladsoft.com.popularmoviesapp.mvp.model;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApiModule;
import ladsoft.com.popularmoviesapp.api.parser.MovieReviewRequestResult;
import ladsoft.com.popularmoviesapp.api.parser.MovieVideosRequestResult;
import ladsoft.com.popularmoviesapp.data.MovieContract;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp;
import retrofit2.Call;
import retrofit2.Response;

import static ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.FAVORITE_ERROR;
import static ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.REVIEW_DATA_LOAD_ERROR;
import static ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.VIDEO_DATA_LOAD_ERROR;

public class MovieDetailsModel implements MovieDetailsMvp.Model<Movie> {
    private static final String apiKey = BuildConfig.API_KEY;
    private final TheMovieDbApi api;
    private String TAG = MovieDetailsModel.class.getSimpleName();
    private final MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo> presenter;

    private final ContentResolver contentResolver;

    public MovieDetailsModel(MovieDetailsMvp.Presenter presenter) {
        this.presenter = presenter;
        this.contentResolver = presenter.getContext().getContentResolver();
        this.api = TheMovieDbApiModule.providesApiAdapter();
    }

    @Override
    public void loadDetails(Movie movie) {
        Cursor cursor = contentResolver.query(MovieContract.MovieEntry.buildMovieUri(movie.getId()),
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            boolean isFavorited = cursor.getShort(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) > 0;
            movie.setFavorite(isFavorited);
        }
        cursor.close();

        presenter.onMovieDetailsLoaded(movie);
    }

    @Override
    public void loadReviews(long id) {
        Call<MovieReviewRequestResult> call = api.getMovieReviews(id, apiKey);
        call.enqueue(reviewRequestCallback);
    }

    @Override
    public void loadVideos(long id) {
        Call<MovieVideosRequestResult> call = api.getMovieVideos(id, apiKey);
        call.enqueue(videoRequestCallback);
    }

    @Override
    public void saveDetails(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_IS_FAVORITE, movie.isFavorite());

        Cursor movieRecord = contentResolver.query(MovieContract.MovieEntry.buildMovieUri(movie.getId()),
                null,
                null,
                null,
                null);
        boolean result;
        if(movieRecord != null && movieRecord.moveToFirst()) {
            result = contentResolver.update(MovieContract.MovieEntry.buildMovieUri(movie.getId()), values, null, null) > 0;
        } else {
            values.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getRawPosterPath());
            values.put(MovieContract.MovieEntry.COLUMN_IS_ADULT, movie.isAdult());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
            values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
            values.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo());
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            result = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values) != null;
        }

        movieRecord.close();

        if(result) {
            presenter.onSaved();
        } else {
            presenter.onError(FAVORITE_ERROR);
        }

    }

    private retrofit2.Callback<MovieReviewRequestResult> reviewRequestCallback = new retrofit2.Callback<MovieReviewRequestResult>() {
        @Override
        public void onResponse(Call<MovieReviewRequestResult> call, Response<MovieReviewRequestResult> response) {
            MovieReviewRequestResult movieReviews = response.body();
            if(movieReviews != null) {
                presenter.onReviewsLoaded(movieReviews.getResults());
            } else {
                presenter.onError(REVIEW_DATA_LOAD_ERROR);
            }
        }

        @Override
        public void onFailure(Call<MovieReviewRequestResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            presenter.onError(REVIEW_DATA_LOAD_ERROR);
        }
    };

    private retrofit2.Callback<MovieVideosRequestResult> videoRequestCallback = new retrofit2.Callback<MovieVideosRequestResult>() {
        @Override
        public void onResponse(Call<MovieVideosRequestResult> call, Response<MovieVideosRequestResult> response) {
            MovieVideosRequestResult movieVideos = response.body();
            if(movieVideos != null) {
                presenter.onVideosLoaded(movieVideos.getResults());
            } else {
                presenter.onError(VIDEO_DATA_LOAD_ERROR);
            }
        }

        @Override
        public void onFailure(Call<MovieVideosRequestResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            presenter.onError(VIDEO_DATA_LOAD_ERROR);
        }
    };
}
