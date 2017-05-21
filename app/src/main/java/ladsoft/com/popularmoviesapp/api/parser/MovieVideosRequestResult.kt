package ladsoft.com.popularmoviesapp.api.parser


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import ladsoft.com.popularmoviesapp.model.MovieVideo

data class MovieVideosRequestResult(@Expose @SerializedName("id") val id: Long = 0.toLong(),
    @Expose @SerializedName("results") val results: List<MovieVideo> = emptyList())
