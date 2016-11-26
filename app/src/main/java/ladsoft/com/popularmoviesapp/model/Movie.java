package ladsoft.com.popularmoviesapp.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {

    public Movie() {}

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Long> genreIds, long id, String originalTitle, String backdropPath, double popularity, long voteCount, boolean video, int voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private List<Long> genreIds;
    private long id;
    private String originalTitle;
    private String backdropPath;
    private double popularity;
    private long voteCount;
    private boolean video;
    private int voteAverage;

    public String getPosterPath() {
        return posterPath;
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

    public int getVoteAverage() {
        return voteAverage;
    }

    protected Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readLong();
        originalTitle = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readLong();
        video = in.readByte() != 0;
        voteAverage = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeLong(id);
        parcel.writeString(originalTitle);
        parcel.writeString(backdropPath);
        parcel.writeDouble(popularity);
        parcel.writeLong(voteCount);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeInt(voteAverage);
    }
}
