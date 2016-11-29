package ladsoft.com.popularmoviesapp.api.parser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieSearchResult {

    public MovieSearchResult() {}

    public MovieSearchResult(long page, List<Movie> result, long totalResults, long totalPages) {
        this.page = page;
        this.result = result;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    @Expose @SerializedName("page") private long page;
    @Expose @SerializedName("results") private List<Movie> result;
    @Expose @SerializedName("total_results") private long totalResults;
    @Expose @SerializedName("total_pages") private long totalPages;

    public long getPage() {
        return page;
    }

    public List<Movie> getResult() {
        return result;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }
}
