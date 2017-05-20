package ladsoft.com.popularmoviesapp.mvp.presenter;

import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.core.mvp.presenter.MvpPresenter;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp;
import ladsoft.com.popularmoviesapp.mvp.MovieDiscoveryMvp;

import static ladsoft.com.popularmoviesapp.mvp.MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES;

public class MovieDiscoveryPresenter extends MvpPresenter<MovieDiscoveryMvp.View<Movie>>
implements MovieDiscoveryMvp.Presenter<MovieDiscoveryMvp.View<Movie>>,  MovieDiscoveryMvp.Model.ModelCallback<Movie> {
    private static final String TAG = MovieDiscoveryPresenter.class.getSimpleName();
    private MovieDiscoveryMvp.Model<Movie> model;
    private MovieSearchResult result;

    public MovieDiscoveryPresenter(MovieDiscoveryMvp.Model<Movie> model) {
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
