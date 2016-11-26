package ladsoft.com.popularmoviesapp.presenter;

import java.util.List;

public interface MovieDiscoveryPresenter<T> {
    enum ErrorType {
        DATA_LOAD_ERROR;
    }

    void loadData();

    interface Callback<T> {
        void onDataLoaded(List<T> movies);
        void onPresenterError(ErrorType errorType);
    }
}
