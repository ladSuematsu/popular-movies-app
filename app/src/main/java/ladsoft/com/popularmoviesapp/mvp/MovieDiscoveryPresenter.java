package ladsoft.com.popularmoviesapp.mvp;

public interface MovieDiscoveryPresenter {
    int SORT_TYPE_MOST_POPULAR = 0;
    int SORT_TYPE_TOP_RATED = 1;
    int SORT_TYPE_USER_FAVORITES = 2;

    void loadData(int sortType);


    enum ErrorType {
        DATA_LOAD_ERROR
    }

    interface Callback<T> {
        void onDataLoaded(T movies);
        void onPresenterError(ErrorType errorType);
    }
}
