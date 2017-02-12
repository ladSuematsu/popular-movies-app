package ladsoft.com.popularmoviesapp.api.parser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ladsoft.com.popularmoviesapp.model.MovieReview;

public class MovieReviewRequestResult {

    public MovieReviewRequestResult() {}

    public MovieReviewRequestResult(long id, long page, List<MovieReview> results, long totalPages, long totalResults) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    @Expose @SerializedName("id") private long id;
    @Expose @SerializedName("page") private long page;
    @Expose @SerializedName("results") private List<MovieReview> results;
    @Expose @SerializedName("total_pages") private long totalPages;
    @Expose @SerializedName("total_results") private long totalResults;

    public long getId() { return id; }

    public long getPage() { return page; }

    public List<MovieReview> getResults() { return results; }

    public long getTotalPages() { return totalPages; }

    public long getTotalResults() { return totalResults; }
}
