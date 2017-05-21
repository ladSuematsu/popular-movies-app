package ladsoft.com.popularmoviesapp.api


import ladsoft.com.popularmoviesapp.BuildConfig
import ladsoft.com.popularmoviesapp.api.parser.MovieReviewRequestResult
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult
import ladsoft.com.popularmoviesapp.api.parser.MovieVideosRequestResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {
    @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_POPULAR)
    fun getMoviesPopular(
            @Query("api_key") apiKey : String)
            : Call<MovieSearchResult>

    @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_TOP_RATED)
    fun getMoviesToptRated(
            @Query("api_key") apiKey : String)
            : Call<MovieSearchResult>

    @GET(BuildConfig.API_ROUTE_MOVIE_REVIEWS)
    fun getMovieReviews(
            @Path("movie_id") movieId : Long,
        @Query("api_key") apiKey : String)
            :Call<MovieReviewRequestResult>

    @GET(BuildConfig.API_ROUTE_MOVIE_VIDEOS)
    fun getMovieVideos(
            @Path("movie_id") movieId : Long,
            @Query("api_key") apiKey : String)
            : Call<MovieVideosRequestResult>
}
