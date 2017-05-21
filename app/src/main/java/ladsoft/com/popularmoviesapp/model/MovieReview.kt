package ladsoft.com.popularmoviesapp.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieReview (@Expose @SerializedName("id") val id: String = "",
    @Expose @SerializedName("author") val author: String = "",
    @Expose @SerializedName("content") val content: String = "")
