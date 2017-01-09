package ladsoft.com.popularmoviesapp.presenter;


import java.util.List;

import ladsoft.com.popularmoviesapp.model.MovieVideo;

public interface MovieDetailsPresenter<T> {
    enum ErrorType {
        DATA_LOAD_ERROR,
        VIDEO_DATA_LOAD_ERROR
    }

    interface Callback<T> {
        void onDataLoaded(T movie);
        void onVideoListLoaded(List<MovieVideo> videos);
        void onFavoriteSuccessful();
        void onError(ErrorType errorType);
    }

    void loadData(T movie);
    void loadMovieVideos();
    String getPosterPath();
    void setFavorite();
}
