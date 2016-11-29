package ladsoft.com.popularmoviesapp.api;


import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {
        String SORT_CRITERIA_POPULARITY = "vote_average.desc";

        @GET(BuildConfig.API_ROUTE_MOVIE_SEARCH)
        Call<MovieSearchResult> getMovies(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortCriteria
        );
}
