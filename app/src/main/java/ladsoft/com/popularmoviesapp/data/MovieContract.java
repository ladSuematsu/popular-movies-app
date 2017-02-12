package ladsoft.com.popularmoviesapp.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "ladsoft.com.popularmoviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_IS_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_GENRE_IDs = "genre_ids";
        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";

        public static final String SQL_CREATE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    MovieEntry.COLUMN_POSTER_PATH + " TEXT," +
                    MovieEntry.COLUMN_IS_ADULT + " INTEGER(1) DEFAULT 0," +
                    MovieEntry.COLUMN_OVERVIEW + " TEXT," +
                    MovieEntry.COLUMN_RELEASE_DATE + " TEXT," +
                    MovieEntry.COLUMN_GENRE_IDs + " TEXT," +
                    MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT," +
                    MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT," +
                    MovieEntry.COLUMN_TITLE + " TEXT," +
                    MovieEntry.COLUMN_BACKDROP_PATH + " TEXT," +
                    MovieEntry.COLUMN_POPULARITY + " TEXT," +
                    MovieEntry.COLUMN_VOTE_COUNT + " TEXT," +
                    MovieEntry.COLUMN_VIDEO + " TEXT," +
                    MovieEntry.COLUMN_VOTE_AVERAGE + " REAL," +
                    MovieEntry.COLUMN_IS_FAVORITE + " INTEGER(1) DEFAULT 0)";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getMovieIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
