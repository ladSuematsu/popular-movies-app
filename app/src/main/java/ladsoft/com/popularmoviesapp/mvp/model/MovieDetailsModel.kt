package ladsoft.com.popularmoviesapp.mvp.model


import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.util.Log

import ladsoft.com.popularmoviesapp.BuildConfig
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi
import ladsoft.com.popularmoviesapp.api.parser.MovieReviewRequestResult
import ladsoft.com.popularmoviesapp.api.parser.MovieVideosRequestResult
import ladsoft.com.popularmoviesapp.core.mvp.model.MvpModel
import ladsoft.com.popularmoviesapp.data.MovieContract
import ladsoft.com.popularmoviesapp.model.Movie
import ladsoft.com.popularmoviesapp.model.MovieReview
import ladsoft.com.popularmoviesapp.model.MovieVideo
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp
import retrofit2.Call
import retrofit2.Response

import ladsoft.com.popularmoviesapp.api.providesApiAdapter
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.FAVORITE_ERROR
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.REVIEW_DATA_LOAD_ERROR
import ladsoft.com.popularmoviesapp.mvp.MovieDetailsMvp.ErrorType.VIDEO_DATA_LOAD_ERROR

class MovieDetailsModel(private val contentResolver: ContentResolver) :
        MvpModel<MovieDetailsMvp.Model.ModelCallback<Movie, MovieReview, MovieVideo>>(),
        MovieDetailsMvp.Model<Movie,
        MovieReview, MovieVideo> {
    private val api: TheMovieDbApi
    private val TAG = MovieDetailsModel::class.java.simpleName

    init {
        this.api = providesApiAdapter()
    }

    override fun loadDetails(movie: Movie) {
        val cursor = contentResolver.query(MovieContract.MovieEntry.buildMovieUri(movie.id), null, null, null, null)

        if (cursor!!.moveToFirst()) {
            val isFavorited = cursor.getShort(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) > 0
            movie.isFavorite = isFavorited
        }
        cursor.close()

        callback.onMovieDetailsLoaded(movie)
    }

    override fun loadReviews(id: Long) {
        val call = api.getMovieReviews(id, apiKey)
        call.enqueue(reviewRequestCallback)
    }

    override fun loadVideos(id: Long) {
        val call = api.getMovieVideos(id, apiKey)
        call.enqueue(videoRequestCallback)
    }

    override fun saveDetails(movie: Movie) {
        val values = ContentValues()
        values.put(MovieContract.MovieEntry.COLUMN_IS_FAVORITE, movie.isFavorite)

        val movieRecord = contentResolver.query(MovieContract.MovieEntry.buildMovieUri(movie.id), null, null, null, null)
        val result: Boolean
        if (movieRecord != null && movieRecord.moveToFirst()) {
            result = contentResolver.update(MovieContract.MovieEntry.buildMovieUri(movie.id), values, null, null) > 0
        } else {
            values.put(MovieContract.MovieEntry.COLUMN_ID, movie.id)
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.rawPosterPath)
            values.put(MovieContract.MovieEntry.COLUMN_IS_ADULT, movie.isAdult)
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.overview)
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.releaseDate)
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.originalTitle)
            values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movie.originalLanguage)
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.title)
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.backdropPath)
            values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.popularity)
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.voteCount)
            values.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo)
            values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.voteAverage)
            result = contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, values) != null
        }

        movieRecord!!.close()

        if (result) {
            callback.onSaved()
        } else {
            callback.onError(FAVORITE_ERROR)
        }

    }

    private val reviewRequestCallback = object : retrofit2.Callback<MovieReviewRequestResult> {
        override fun onResponse(call: Call<MovieReviewRequestResult>, response: Response<MovieReviewRequestResult>) {
            val movieReviews = response.body()
            if (movieReviews != null) {
                callback.onReviewsLoaded(movieReviews.results)
            } else {
                callback.onError(REVIEW_DATA_LOAD_ERROR)
            }
        }

        override fun onFailure(call: Call<MovieReviewRequestResult>, t: Throwable) {
            Log.e(TAG, t.localizedMessage, t)
            callback.onError(REVIEW_DATA_LOAD_ERROR)
        }
    }

    private val videoRequestCallback = object : retrofit2.Callback<MovieVideosRequestResult> {
        override fun onResponse(call: Call<MovieVideosRequestResult>, response: Response<MovieVideosRequestResult>) {
            val movieVideos = response.body()
            if (movieVideos != null) {
                callback.onVideosLoaded(movieVideos.results)
            } else {
                callback.onError(VIDEO_DATA_LOAD_ERROR)
            }
        }

        override fun onFailure(call: Call<MovieVideosRequestResult>, t: Throwable) {
            Log.e(TAG, t.localizedMessage, t)
            callback.onError(VIDEO_DATA_LOAD_ERROR)
        }
    }

    companion object {
        private val apiKey = BuildConfig.API_KEY
    }
}
