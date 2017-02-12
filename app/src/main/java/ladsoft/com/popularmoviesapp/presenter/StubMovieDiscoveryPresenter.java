package ladsoft.com.popularmoviesapp.presenter;


import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.model.Movie;

public class StubMovieDiscoveryPresenter implements MovieDiscoveryPresenter<Movie> {

    private List<Movie> movies;
    private Callback<MovieSearchResult> callback;

    public StubMovieDiscoveryPresenter(@NonNull Callback<MovieSearchResult> callback) {
        this.callback = callback;
    }

    @Override
    public void loadData(int sortType) {
        movies = new ArrayList<>();

        Movie movie = new Movie("/kqjL17yufvn9OVLyXYpvtyrFfak.jpg", false,
                "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum " +
                        "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
                "", null, 1, "Generic Movie Long Original Title", "en", "", "", 3.8D, 123L, false, 4, true);

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

        callback.onDataLoaded(new MovieSearchResult(1, movies, 123, 123));
    }
}
