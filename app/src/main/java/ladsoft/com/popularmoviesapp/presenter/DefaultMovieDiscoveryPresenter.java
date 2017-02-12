package ladsoft.com.popularmoviesapp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.model.Movie;

import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES;

public class DefaultMovieDiscoveryPresenter implements MovieDiscoveryMvp.Presenter<Movie> {
    private static final String TAG = DefaultMovieDiscoveryPresenter.class.getSimpleName();
    private final MovieDiscoveryMvp.Model<Movie> model;
    private final WeakReference<MovieDiscoveryMvp.View<Movie>> view;
    private MovieSearchResult result;

    public DefaultMovieDiscoveryPresenter(MovieDiscoveryMvp.View<Movie> view) {
        this.view = new WeakReference<>(view);
        this.model = new MovieDiscoveryModel(this);
    }

    @Override
    public Context getContext() {
        return ((Fragment) view.get()).getContext();
    }

    @Override
    public void loadData(int sortType) {
        if (sortType == SORT_TYPE_USER_FAVORITES) {
            view.get().showFavorites();
        } else {
            model.loadMovies(sortType);
        }
    }

    @Override
    public void onDataLoaded(List<Movie> data) {
        view.get().refreshMovies(data);
    }

    @Override
    public void onError(MovieDetailsMvp.ErrorType errorType) {
        switch(errorType) {
            case DATA_LOAD_ERROR:
                view.get().showSnackbar(R.string.movie_discovery_data_load_error);
                break;

            default:
                view.get().showSnackbar(R.string.movie_discovery_error);
        }
    }
}
