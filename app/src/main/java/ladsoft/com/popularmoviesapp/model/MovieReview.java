package ladsoft.com.popularmoviesapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieReview {

    public MovieReview() {}

    public MovieReview(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    @Expose @SerializedName("id") private String id;
    @Expose @SerializedName("page") private String author;
    @Expose @SerializedName("content") private String content;

    public String getId() { return id; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
}
