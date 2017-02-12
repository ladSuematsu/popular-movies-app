package ladsoft.com.popularmoviesapp.presenter;


import android.content.Context;
import android.database.Cursor;

import java.util.List;

public interface MovieDiscoveryMvp {
    int SORT_TYPE_MOST_POPULAR = 0;
    int SORT_TYPE_TOP_RATED = 1;
    int SORT_TYPE_USER_FAVORITES = 2;

    enum ErrorType {
        DATA_LOAD_ERROR,
    }

    interface Model<T> {
        void loadMovies(int type);
    }

    interface Presenter<S> {
        Context getContext();
        void loadData(int sortType);
        void onDataLoaded(List<S> data);
        void onError(MovieDetailsMvp.ErrorType errorType);
    }

    interface View<S> {
        void refreshMovies(List<S> videos);
        void showSnackbar(int messageResourceId);
        void showFavorites();
    }

}
