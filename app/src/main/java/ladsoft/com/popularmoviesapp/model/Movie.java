package ladsoft.com.popularmoviesapp.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ladsoft.com.popularmoviesapp.BuildConfig;

public class Movie implements Parcelable {

    private static final String baseImageUrl = BuildConfig.BASE_IMAGE_URL;

    public Movie() {}

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Long> genreIds, long id, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, long voteCount, boolean video, int voteAverage, boolean favorite) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.favorite = favorite;
    }

    @Expose @SerializedName("poster_path") private String posterPath;
    @Expose @SerializedName("is_adult") private boolean adult;
    @Expose @SerializedName("overview") private String overview;
    @Expose @SerializedName("release_date") private String releaseDate;
    @Expose @SerializedName("genre_ids") private List<Long> genreIds;
    @Expose @SerializedName("id") private long id;
    @Expose @SerializedName("original_title") private String originalTitle;
    @Expose @SerializedName("original_language") private String originalLanguage;
    @Expose @SerializedName("title") private String title;
    @Expose @SerializedName("backdrop_path") private String backdropPath;
    @Expose @SerializedName("popularity") private double popularity;
    @Expose @SerializedName("vote_count") private long voteCount;
    @Expose @SerializedName("video") private boolean video;
    @Expose @SerializedName("vote_average") private double voteAverage;
    private boolean favorite;

    protected Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readLong();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readLong();
        video = in.readByte() != 0;
        voteAverage = in.readDouble();
        favorite = in.readByte() != 0;
//        in.readList(genreIds,  List.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeLong(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeLong(voteCount);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeByte((byte) (favorite ? 1 : 0));
//        dest.writeList(genreIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return baseImageUrl + posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Long> getGenreIds() { return genreIds; }

    public long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
