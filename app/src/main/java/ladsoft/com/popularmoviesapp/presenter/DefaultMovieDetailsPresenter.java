package ladsoft.com.popularmoviesapp.presenter;


import ladsoft.com.popularmoviesapp.model.Movie;

public class DefaultMovieDetailsPresenter implements MovieDetailsPresenter<Movie> {

    private Movie movie;
    private Callback callback;

    public DefaultMovieDetailsPresenter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void loadData(Movie movie) {
        this.movie = movie;

        callback.onDataLoaded(movie);
    }

    @Override
    public String getPosterPath() {
        return movie.getPosterPath();
    }

    @Override
    public void setFavorite() {
        callback.onFavoriteSuccessful();
    }
}
