package ladsoft.com.popularmoviesapp.mvp


import android.content.Context
import android.net.Uri

import ladsoft.com.popularmoviesapp.core.mvp.Mvp

interface MovieDetailsMvp {
    enum class ErrorType {
        DATA_LOAD_ERROR,
        VIDEO_DATA_LOAD_ERROR,
        VIDEO_LINK_PARSE_ERROR,
        REVIEW_DATA_LOAD_ERROR,
        FAVORITE_ERROR
    }

    interface Model<S, out T, out U> : Mvp.Model<Model.ModelCallback<S, T, U>> {
        fun loadDetails(data: S)
        fun loadReviews(id: Long)
        fun loadVideos(id: Long)
        fun saveDetails(data: S)

        interface ModelCallback<in S,in T,in U> : Mvp.Model.ModelCallback {
            fun onError(errorType: MovieDetailsMvp.ErrorType)
            fun onMovieDetailsLoaded(data: S)
            fun onReviewsLoaded(reviews: List<T>)
            fun onVideosLoaded(videos: List<U>)
            fun onSaved()
        }
    }

    interface Presenter<S, T, U> : Mvp.Presenter<View<S, T, U>> {
        fun loadData(movie: S)
        fun loadVideos()
        fun loadReviews()
        fun onMovieVideoSelected(context: Context, video: U)
        val posterPath: String
        fun setFavorite()
    }

    interface View<in S, in T, in U> {
        fun onDataLoaded(movie: S)
        fun refreshReviews(reviews: List<T>)
        fun refreshVideos(videos: List<U>)
        fun onVideoLaunch(videoUri: Uri)
        fun onFavoriteSuccessful(favorite: Boolean)
        fun showSnackbar(messageResourceId: Int)
        fun showVideosEmptyMessage(show: Boolean)
        fun showVideosLoadError(show: Boolean)
        fun showReviewsEmptyMessage(show: Boolean)
        fun showReviewsLoadError(show: Boolean)
    }
}
