package ladsoft.com.popularmoviesapp.presenter;


import ladsoft.com.popularmoviesapp.model.Movie;

public interface MovieDetailsPresenter<T> {
    interface Callback<T> {
        void onDataLoaded(T movie);
        void onFavoriteSuccessful();
        void onError();
    }

    void loadData(T movie);
    String getPosterPath();
    void setFavorite();
}
