package ladsoft.com.popularmoviesapp.presenter;

import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.core.mvp.presenter.MvpPresenter;
import ladsoft.com.popularmoviesapp.model.Movie;

import static ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES;

public class DefaultMovieDiscoveryPresenter extends MvpPresenter<MovieDiscoveryMvp.View<Movie>>
implements MovieDiscoveryMvp.Presenter<MovieDiscoveryMvp.View<Movie>>,  MovieDiscoveryMvp.Model.ModelCallback<Movie> {
    private static final String TAG = DefaultMovieDiscoveryPresenter.class.getSimpleName();
    private MovieDiscoveryMvp.Model<Movie> model;
    private MovieSearchResult result;

    public DefaultMovieDiscoveryPresenter(MovieDiscoveryMvp.Model<Movie> model) {
        this.model = model;
        this.model.attach(this);
    }

    @Override
    public void loadData(int sortType) {
        if (sortType == SORT_TYPE_USER_FAVORITES) {
            if(isViewAttached()) {
                getView().showFavorites();
            }
        } else {
            model.loadMovies(sortType);
        }
    }

    @Override
    public void onError(MovieDetailsMvp.ErrorType errorType) {
        if (isViewAttached()) {
            switch (errorType) {
                case DATA_LOAD_ERROR:
                    getView().showSnackbar(R.string.movie_discovery_data_load_error);

                break;

                default:
                    getView().showSnackbar(R.string.movie_discovery_error);
            }
        }
    }

    @Override
    public void onDataLoaded(List<Movie> data) {
        if(isViewAttached()) {
            getView().refreshMovies(data);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        model.detach();
    }
}
