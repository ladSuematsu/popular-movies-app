package ladsoft.com.popularmoviesapp.mvp;


import java.util.List;

import ladsoft.com.popularmoviesapp.core.mvp.Mvp;

public interface MovieDiscoveryMvp {
    int SORT_TYPE_MOST_POPULAR = 0;
    int SORT_TYPE_TOP_RATED = 1;
    int SORT_TYPE_USER_FAVORITES = 2;

    enum ErrorType {
        DATA_LOAD_ERROR,
    }

    interface Model<T> extends Mvp.Model<Model.ModelCallback<T>>{
        void loadMovies(int type);

        interface ModelCallback<T> extends Mvp.Model.ModelCallback {
            void onDataLoaded(List<T> data);
            void onError(MovieDetailsMvp.ErrorType errorType);
        }
    }

    interface Presenter<V> extends Mvp.Presenter<V> {
        void loadData(int sortType);
    }

    interface View<S> {
        void refreshMovies(List<S> videos);
        void showSnackbar(int messageResourceId);
        void showFavorites();
    }

}
