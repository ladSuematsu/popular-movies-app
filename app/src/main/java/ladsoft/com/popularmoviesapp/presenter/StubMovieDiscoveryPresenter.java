package ladsoft.com.popularmoviesapp.presenter;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.model.Movie;

public class StubMovieDiscoveryPresenter implements MovieDiscoveryPresenter<Movie> {

    private List<Movie> movies;
    private Callback<Movie> callback;

    public StubMovieDiscoveryPresenter(@NonNull Callback<Movie> callback) {
        this.callback = callback;
    }

    @Override
    public void loadData() {
        movies = new ArrayList<>();

        Movie movie = new Movie("https://image.tmdb.org/t/p/w342/kqjL17yufvn9OVLyXYpvtyrFfak.jpg", false,
                "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
                "", null, 1, "Generic Movie Long Original Title", "", 3.8D, 123, false, 4);

        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);

        callback.onDataLoaded(movies);
    }
}
