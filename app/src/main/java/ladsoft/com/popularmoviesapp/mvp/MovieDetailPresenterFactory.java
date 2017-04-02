package ladsoft.com.popularmoviesapp.mvp;


import android.support.annotation.NonNull;

import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;
import ladsoft.com.popularmoviesapp.mvp.presenter.MovieDetailsPresenter;

public class MovieDetailPresenterFactory {
    public static MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo> create(@NonNull MovieDetailsMvp.Model<Movie, MovieReview, MovieVideo> model) {
//        if(BuildConfig.DATA_STUB_MODE) {
//
//        } else {
            return new MovieDetailsPresenter(model);
//        }
    }
}
