package ladsoft.com.popularmoviesapp.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieVideo (@Expose @SerializedName("id") val id: String = "",
    @Expose @SerializedName("iso_6391_1") val iso6391: String = "",
    @Expose @SerializedName("iso_3166_1") val iso31661: String = "",
    @Expose @SerializedName("key") val key: String = "",
    @Expose @SerializedName("name") val name: String = "",
    @Expose @SerializedName("site") val site: String = "",
    @Expose @SerializedName("size") val size: Long = 0.toLong(),
    @Expose @SerializedName("type") val type: String = "")
