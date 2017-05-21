package ladsoft.com.popularmoviesapp.api.parser


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import ladsoft.com.popularmoviesapp.model.Movie

data class MovieSearchResult(@Expose @SerializedName("page") val page: Long = 0.toLong(),
    @Expose @SerializedName("results") val result: List<Movie> = emptyList(),
    @Expose @SerializedName("total_results") val totalResults: Long = 0.toLong() ,
    @Expose @SerializedName("total_pages") val totalPages: Long = 0.toLong())
