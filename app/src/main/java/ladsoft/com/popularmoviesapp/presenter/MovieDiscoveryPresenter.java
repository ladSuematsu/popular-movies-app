package ladsoft.com.popularmoviesapp.presenter;

public interface MovieDiscoveryPresenter<T> {
    int SORT_TYPE_MOST_POPULAR = 0;
    int SORT_TYPE_TOP_RATED = 1;


    enum ErrorType {
        DATA_LOAD_ERROR;
    }

    void loadData(int sortType);

    interface Callback<T> {
        void onDataLoaded(T movies);
        void onPresenterError(ErrorType errorType);
    }
}
