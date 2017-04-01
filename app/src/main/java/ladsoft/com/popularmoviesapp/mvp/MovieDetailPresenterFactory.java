package ladsoft.com.popularmoviesapp.mvp;


import android.support.annotation.NonNull;

import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieReview;
import ladsoft.com.popularmoviesapp.model.MovieVideo;

public class MovieDetailPresenterFactory {
    public static MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo> create(@NonNull  MovieDetailsMvp.View<Movie, MovieReview, MovieVideo> view) {
//        if(BuildConfig.DATA_STUB_MODE) {
//
//        } else {
            return new MovieDetailsPresenter(view);
//        }
    }
}
