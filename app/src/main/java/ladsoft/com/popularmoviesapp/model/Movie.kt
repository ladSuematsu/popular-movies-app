package ladsoft.com.popularmoviesapp.model


import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

import ladsoft.com.popularmoviesapp.BuildConfig
import ladsoft.com.popularmoviesapp.data.MovieContract

data class Movie(@Expose @SerializedName("poster_path") var rawPosterPath: String = "",
    @Expose @SerializedName("is_adult") var isAdult: Boolean = false,
    @Expose @SerializedName("overview") var overview: String = "",
    @Expose @SerializedName("release_date") var releaseDate: String = "",
    @Expose @SerializedName("genre_ids") var genreIds: List<Long> = emptyList(),
    @Expose @SerializedName("id") var id: Long = 0.toLong(),
    @Expose @SerializedName("original_title") var originalTitle: String = "",
    @Expose @SerializedName("original_language") var originalLanguage: String = "",
    @Expose @SerializedName("title") var title: String? = null,
    @Expose @SerializedName("backdrop_path") var backdropPath: String = "",
    @Expose @SerializedName("popularity") var popularity: Double = 0.toDouble(),
    @Expose @SerializedName("vote_count") var voteCount: Long = 0.toLong(),
    @Expose @SerializedName("video") var isVideo: Boolean = false,
    @Expose @SerializedName("vote_average") var voteAverage: Double = 0.toDouble(),
    var isFavorite: Boolean = false) : Parcelable {

    constructor(cursor: Cursor) : this() {
        this.id = cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID))
        this.rawPosterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH))
        this.isAdult = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_ADULT)) == 1
        this.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW))
        this.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))
        this.genreIds = ArrayList<Long>()
        this.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE))
        this.originalLanguage = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE))
        this.title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE))
        this.backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH))
        this.popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY))
        this.voteCount = cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT))
        this.isVideo = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VIDEO)) == 1
        this.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE))
        this.isFavorite = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) == 1
    }

    protected constructor(`in`: Parcel) : this() {
        rawPosterPath = `in`.readString()
        isAdult = `in`.readByte().toInt() != 0
        overview = `in`.readString()
        releaseDate = `in`.readString()
        id = `in`.readLong()
        originalTitle = `in`.readString()
        originalLanguage = `in`.readString()
        title = `in`.readString()
        backdropPath = `in`.readString()
        popularity = `in`.readDouble()
        voteCount = `in`.readLong()
        isVideo = `in`.readByte().toInt() != 0
        voteAverage = `in`.readDouble()
        isFavorite = `in`.readByte().toInt() != 0
        //        in.readList(genreIds,  List.class.getClassLoader());
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(rawPosterPath)
        dest.writeByte((if (isAdult) 1 else 0).toByte())
        dest.writeString(overview)
        dest.writeString(releaseDate)
        dest.writeLong(id)
        dest.writeString(originalTitle)
        dest.writeString(originalLanguage)
        dest.writeString(title)
        dest.writeString(backdropPath)
        dest.writeDouble(popularity)
        dest.writeLong(voteCount)
        dest.writeByte((if (isVideo) 1 else 0).toByte())
        dest.writeDouble(voteAverage)
        dest.writeByte((if (isFavorite) 1 else 0).toByte())
        //        dest.writeList(genreIds);
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        private val baseImageUrl = BuildConfig.BASE_IMAGE_URL

        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(`in`: Parcel): Movie {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }
    }

    val posterPath: String
        get() = baseImageUrl + rawPosterPath!!
}
