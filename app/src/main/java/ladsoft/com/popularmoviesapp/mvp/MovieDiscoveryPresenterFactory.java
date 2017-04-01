package ladsoft.com.popularmoviesapp.mvp;


import android.support.annotation.NonNull;

import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDiscoveryPresenterFactory {

    public static MovieDiscoveryMvp.Presenter create(@NonNull MovieDiscoveryMvp.Model<Movie> model) {
//        if(BuildConfig.DATA_STUB_MODE) {
//            return new StubMovieDiscoveryPresenter(callback);
//        } else {
            return new DefaultMovieDiscoveryPresenter(model);
//        }
    }
}
