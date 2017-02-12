package ladsoft.com.popularmoviesapp.api.parser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ladsoft.com.popularmoviesapp.model.MovieVideo;

public class MovieVideosRequestResult {

    @Expose @SerializedName("id") private long id;
    @Expose @SerializedName("results") private List<MovieVideo> results;

    public long getId() { return id; }
    public List<MovieVideo> getResults() { return results; }
}
