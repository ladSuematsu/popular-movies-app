package ladsoft.com.popularmoviesapp.presenter;


import android.support.annotation.NonNull;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDetailPresenterFactory {
    public static MovieDetailsPresenter<Movie> create(@NonNull MovieDetailsPresenter.Callback<Movie> callback) {
//        if(BuildConfig.DATA_STUB_MODE) {
//
//        } else {
            return new DefaultMovieDetailsPresenter(callback);
//        }
    }
}
