package ladsoft.com.popularmoviesapp.presenter;


import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApiModule;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.data.MovieContract;
import ladsoft.com.popularmoviesapp.model.Movie;
import retrofit2.Call;
import retrofit2.Response;

import static ladsoft.com.popularmoviesapp.presenter.MovieDetailsMvp.ErrorType.DATA_LOAD_ERROR;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_MOST_POPULAR;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_TOP_RATED;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES;


public class MovieDiscoveryModel implements MovieDiscoveryMvp.Model<Movie> {
    private static final String apiKey = BuildConfig.API_KEY;
    private final TheMovieDbApi api;
    private String TAG = MovieDiscoveryModel.class.getSimpleName();
    private final MovieDiscoveryMvp.Presenter<Movie> presenter;

    private final ContentResolver contentResolver;

    public MovieDiscoveryModel(MovieDiscoveryMvp.Presenter presenter) {
        this.presenter = presenter;
        this.contentResolver = presenter.getContext().getContentResolver();
        this.api = TheMovieDbApiModule.providesApiAdapter();
    }


    @Override
    public void loadMovies(int sortType) {
        Call<MovieSearchResult> call;
        switch(sortType) {
            case SORT_TYPE_TOP_RATED:
                call = api.getMoviesToptRated(apiKey);
                call.enqueue(callback);
                break;

            case SORT_TYPE_USER_FAVORITES:
                getUserFavoriteMovies();
                break;
            case SORT_TYPE_MOST_POPULAR:
            default:
                call = api.getMoviesPopular(apiKey);
                call.enqueue(callback);
        }
    }

    private void getUserFavoriteMovies() {
        Cursor result = contentResolver.query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_IS_FAVORITE + "=?",
                new String[] {"1"},
                null
        );

        Log.d(TAG, "Favorite movies: " + result.getCount());

        presenter.onFavoritesLoaded(result);
    }

    private retrofit2.Callback<MovieSearchResult> callback = new retrofit2.Callback<MovieSearchResult>() {
        @Override
        public void onResponse(Call<MovieSearchResult> call, Response<MovieSearchResult> response) {
            MovieSearchResult result = response.body();
            if(result != null) {
                presenter.onDataLoaded(result.getResult());
            } else {
                presenter.onError(DATA_LOAD_ERROR);

            }
        }

        @Override
        public void onFailure(Call<MovieSearchResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            presenter.onError(DATA_LOAD_ERROR);
        }
    };
}
