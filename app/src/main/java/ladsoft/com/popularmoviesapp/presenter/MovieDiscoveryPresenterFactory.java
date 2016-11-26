package ladsoft.com.popularmoviesapp.presenter;


import android.support.annotation.NonNull;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDiscoveryPresenterFactory {

    public static MovieDiscoveryPresenter<Movie> create(@NonNull MovieDiscoveryPresenter.Callback<Movie> callback) {
        if(BuildConfig.DATA_STUB_MODE) {
            return new StubMovieDiscoveryPresenter(callback);
        } else {
            return new DefaultMovieDiscoveryPresenter(callback);
        }
    }
}
