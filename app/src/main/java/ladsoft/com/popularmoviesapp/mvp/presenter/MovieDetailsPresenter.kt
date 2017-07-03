package ladsoft.com.popularmoviesapp.mvp.presenter


import android.content.Context
import android.net.Uri
import android.util.Log

import java.util.ArrayList

import ladsoft.com.popularmoviesapp.R
import ladsoft.com.popularmoviesapp.core.mvp.presenter.MvpPresenter
import ladsoft.com.popularmoviesapp.model.Movie
import ladsoft.com.popularmoviesapp.model.MovieReview
import ladsoft.com.popularmoviesapp.model.MovieVideo
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp

import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.VIDEO_LINK_PARSE_ERROR

class MovieDetailsPresenter(private val model: MovieDetailsMvp.Model<Movie, MovieReview, MovieVideo>) :
        MvpPresenter<MovieDetailsMvp.View<Movie, MovieReview, MovieVideo>>(),
        MovieDetailsMvp.Presenter<Movie, MovieReview, MovieVideo>,
        MovieDetailsMvp.Model.ModelCallback<Movie, MovieReview, MovieVideo> {
    private var movie: Movie? = null
    private var reviews: List<MovieReview>? = null
    private var videos: List<MovieVideo>? = null

    init {
        this.model.attach(this)
        reviews = ArrayList<MovieReview>()
        videos = ArrayList<MovieVideo>()
    }

    override fun loadData(movie: Movie) {
        this.movie = movie
        model.loadDetails(movie)
    }

    override fun loadVideos() {
        model.loadVideos(movie!!.id)
    }

    override fun loadReviews() {
        model.loadReviews(movie!!.id)
    }

    override fun onMovieDetailsLoaded(data: Movie) {
        this.movie = data
        if (isViewAttached) {
            getView()?.onDataLoaded(movie!!)
        }
    }

    override fun onReviewsLoaded(reviews: List<MovieReview>) {
        this.reviews = reviews
        if (isViewAttached) {
            getView()?.refreshReviews(reviews)
        }
    }

    override fun onVideosLoaded(videos: List<MovieVideo>) {
        this.videos = videos
        if (isViewAttached) {
            getView()?.refreshVideos(videos)
        }
    }

    override fun onSaved() {
        getView()?.onFavoriteSuccessful(movie!!.isFavorite)
    }

    override fun onMovieVideoSelected(context: Context, video: MovieVideo) {
        var videoUri: Uri? = null

        try {
            val linkFormat = context.getString(R.string.youtube_video_link_format)
            val videoLink = String.format(linkFormat, video.key)
            videoUri = Uri.parse(videoLink)
        } catch (e: NullPointerException) {
            Log.e(TAG, "Invalid key value", e)
        }

        if (videoUri != null) {
            getView()?.onVideoLaunch(videoUri)
        } else {
            onError(VIDEO_LINK_PARSE_ERROR)
        }
    }

    override val posterPath: String
        get() = movie!!.posterPath

    override fun setFavorite() {
        movie!!.isFavorite = !movie!!.isFavorite
        model.saveDetails(movie!!)
    }

    override fun onError(errorType: MovieDetailsMvp.ErrorType) {
        when (errorType) {
            MovieDetailsMvp.ErrorType.VIDEO_DATA_LOAD_ERROR -> {
                if (isViewAttached) {
                    getView()?.showVideosLoadError(true)
                }
                return
            }

            VIDEO_LINK_PARSE_ERROR -> if (isViewAttached) {
                getView()?.showSnackbar(R.string.movie_details_error_generic)
            }

            MovieDetailsMvp.ErrorType.REVIEW_DATA_LOAD_ERROR -> {
                if (isViewAttached) {
                    getView()?.showReviewsLoadError(true)
                }
                return
            }
            MovieDetailsMvp.ErrorType.FAVORITE_ERROR -> {
                movie!!.isFavorite = !movie!!.isFavorite
                if (isViewAttached) {
                    getView()?.onFavoriteSuccessful(movie!!.isFavorite)
                }
            }
        }
    }

    companion object {
        private val TAG = MovieDetailsPresenter::class.java.simpleName
    }

}
