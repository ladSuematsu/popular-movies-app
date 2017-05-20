package ladsoft.com.popularmoviesapp.mvp;


import android.content.Context;
import android.net.Uri;

import java.util.List;

import ladsoft.com.popularmoviesapp.core.mvp.Mvp;

public interface MovieDetailsMvp {
    enum ErrorType {
        DATA_LOAD_ERROR,
        VIDEO_DATA_LOAD_ERROR,
        VIDEO_LINK_PARSE_ERROR,
        REVIEW_DATA_LOAD_ERROR,
        FAVORITE_ERROR
    }

    interface Model<S, T, U> extends Mvp.Model<Model.ModelCallback<S, T, U>>{
        void loadDetails(S data);
        void loadReviews(long id);
        void loadVideos(long id);
        void saveDetails(S data);

        interface ModelCallback<S, T, U> extends Mvp.Model.ModelCallback {
            void onError(MovieDetailsMvp.ErrorType errorType);
            void onMovieDetailsLoaded(S data);
            void onReviewsLoaded(List<T> reviews);
            void onVideosLoaded(List<U> videos);
            void onSaved();
        }
    }

    interface Presenter<S, T, U> extends Mvp.Presenter<View<S,T,U>>{
        void loadData(S movie);
        void loadVideos();
        void loadReviews();
        void onMovieVideoSelected(Context context, U video);
        String getPosterPath();
        void setFavorite();
    }

    interface View<S, T, U> {
        void onDataLoaded(S movie);
        void refreshReviews(List<T> reviews);
        void refreshVideos(List<U> videos);
        void onVideoLaunch(Uri videoUri);
        void onFavoriteSuccessful(boolean favorite);
        void showSnackbar(int messageResourceId);
        void showVideosEmptyMessage(boolean show);
        void showVideosLoadError(boolean show);
        void showReviewsEmptyMessage(boolean show);
        void showReviewsLoadError(boolean show);
    }
}
