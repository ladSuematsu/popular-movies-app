package ladsoft.com.popularmoviesapp.mvp;


import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface MovieDetailsMvp {
    enum ErrorType {
        DATA_LOAD_ERROR,
        VIDEO_DATA_LOAD_ERROR,
        VIDEO_LINK_PARSE_ERROR,
        REVIEW_DATA_LOAD_ERROR,
        FAVORITE_ERROR
    }

    interface Model<T> {
        void loadDetails(T data);
        void loadReviews(long id);
        void loadVideos(long id);
        void saveDetails(T data);
    }

    interface Presenter<S, T, U> {
        Context getContext();
        void loadData(S movie);
        void loadVideos();
        void loadReviews();
        void onMovieDetailsLoaded(S data);
        void onReviewsLoaded(List<T> reviews);
        void onVideosLoaded(List<U> videos);
        void onSaved();
        void onMovieVideoSelected(Context context, U video);
        String getPosterPath();
        void setFavorite();
        void onError(ErrorType errorType);
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
