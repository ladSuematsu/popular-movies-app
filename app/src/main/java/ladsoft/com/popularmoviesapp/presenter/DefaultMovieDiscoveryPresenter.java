package ladsoft.com.popularmoviesapp.presenter;

import android.util.Log;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApiModule;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.model.Movie;
import retrofit2.Call;
import retrofit2.Response;

public class DefaultMovieDiscoveryPresenter implements MovieDiscoveryPresenter<Movie> {
    private static final String TAG = DefaultMovieDiscoveryPresenter.class.getSimpleName();
    private final Callback<MovieSearchResult> presenterCallback;
    private final TheMovieDbApi api;
    private final String apiKey = BuildConfig.API_KEY;
    private MovieSearchResult result;

    public DefaultMovieDiscoveryPresenter(Callback<MovieSearchResult> presenterCallback) {
        this.presenterCallback = presenterCallback;
        this.api = TheMovieDbApiModule.providesApiAdapter();
    }

    @Override
    public void loadData(int sortType) {
        Call<MovieSearchResult> call;
        switch(sortType) {
            case SORT_TYPE_TOP_RATED:
                call = api.getMoviesToptRated(apiKey);
                break;
            case SORT_TYPE_MOST_POPULAR:
            default:
                call = api.getMoviesPopular(apiKey);
        }

        call.enqueue(callback);
    }

    private retrofit2.Callback<MovieSearchResult> callback = new retrofit2.Callback<MovieSearchResult>() {
        @Override
        public void onResponse(Call<MovieSearchResult> call, Response<MovieSearchResult> response) {
            result = response.body();
            if(result != null) {
                presenterCallback.onDataLoaded(result);
            } else {
                presenterCallback.onPresenterError(ErrorType.DATA_LOAD_ERROR);

            }
        }

        @Override
        public void onFailure(Call<MovieSearchResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            presenterCallback.onPresenterError(ErrorType.DATA_LOAD_ERROR);
        }
    };
}
