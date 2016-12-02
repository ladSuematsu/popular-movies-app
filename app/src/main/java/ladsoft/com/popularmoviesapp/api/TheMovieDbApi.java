package ladsoft.com.popularmoviesapp.api;


import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {
        String SORT_CRITERIA_MOST_POPULAR = "popularity.desc";
        String SORT_CRITERIA_HIGHEST_RATED = "vote_average.desc";

        @GET(BuildConfig.API_ROUTE_MOVIE_SEARCH)
        Call<MovieSearchResult> getMovies(
                @Query("sort_by") String sortCriteria,
                @Query("api_key") String apiKey
        );

        @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_POPULAR)
        Call<MovieSearchResult> getMoviesPopular(
                @Query("api_key") String apiKey
        );

        @GET(BuildConfig.API_ROUTE_MOVIE_DISCOVERY_TOP_RATED)
        Call<MovieSearchResult> getMoviesToptRated(
                @Query("api_key") String apiKey
        );
}
