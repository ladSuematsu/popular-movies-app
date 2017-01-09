package ladsoft.com.popularmoviesapp.api;


import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.parser.MovieReviewRequestResult;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {
    @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_POPULAR)
    Call<MovieSearchResult> getMoviesPopular(
        @Query("api_key") String apiKey
        );

    @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_TOP_RATED)
    Call<MovieSearchResult> getMoviesToptRated(
        @Query("api_key") String apiKey
        );

    @GET(BuildConfig.API_ROUTE_MOVIE_REVIEWS)
    Call<MovieReviewRequestResult> getMovieReviews(
        @Path("movie_id") long movieId,
        @Query("api_key") String apiKey
        );
}
