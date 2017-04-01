package ladsoft.com.popularmoviesapp.presenter;


import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApiModule;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.core.mvp.model.MvpModel;
import ladsoft.com.popularmoviesapp.data.MovieContract;
import ladsoft.com.popularmoviesapp.model.Movie;
import retrofit2.Call;
import retrofit2.Response;

import static ladsoft.com.popularmoviesapp.presenter.MovieDetailsMvp.ErrorType.DATA_LOAD_ERROR;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_MOST_POPULAR;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_TOP_RATED;
import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES;


public class MovieDiscoveryModel extends MvpModel<MovieDiscoveryMvp.Model.ModelCallback<Movie>> implements MovieDiscoveryMvp.Model<Movie> {
    private static final String apiKey = BuildConfig.API_KEY;
    private final TheMovieDbApi api;
    private String TAG = MovieDiscoveryModel.class.getSimpleName();

    private final ContentResolver contentResolver;

    public MovieDiscoveryModel(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
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

            case SORT_TYPE_MOST_POPULAR:
            default:
                call = api.getMoviesPopular(apiKey);
                call.enqueue(callback);
        }
    }

    private retrofit2.Callback<MovieSearchResult> callback = new retrofit2.Callback<MovieSearchResult>() {
        @Override
        public void onResponse(Call<MovieSearchResult> call, Response<MovieSearchResult> response) {
            MovieSearchResult result = response.body();
            if(result != null) {
                getCallback().onDataLoaded(result.getResult());
            } else {
                getCallback().onError(DATA_LOAD_ERROR);

            }
        }

        @Override
        public void onFailure(Call<MovieSearchResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            getCallback().onError(DATA_LOAD_ERROR);
        }
    };
}
