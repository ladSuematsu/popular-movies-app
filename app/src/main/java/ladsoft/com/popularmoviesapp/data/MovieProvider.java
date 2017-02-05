package ladsoft.com.popularmoviesapp.data;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import ladsoft.com.popularmoviesapp.data.util.SqlExpressionBuilder;

public class MovieProvider extends ContentProvider{

    private static final UriMatcher uriMatcher;
    private static final int MOVIE = 100;
    private static final int MOVIE_ITEM = 101;

    private SQLiteOpenHelper dbHelper;

    static {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/*", MOVIE_ITEM);

        uriMatcher = matcher;
    }

    @Override
    public boolean onCreate() {
        try {
            dbHelper = new MovieDbHelper(getContext());
        } catch (Exception e) {
            dbHelper = null;
        }

        return dbHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor result = null;
        if(uriMatcher.match(uri) == MOVIE) {
            result = database.query(MovieContract.MovieEntry.TABLE_NAME,
                    null, s, strings1, null, null, null, null);
        } else if (uriMatcher.match(uri) == MOVIE_ITEM) {
            result = findMovieById(uri);
        }

        return result;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int uriCode = uriMatcher.match(uri);
        switch(uriCode) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;

            default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case MOVIE:
                long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if(id > 0) {
                    return MovieContract.MovieEntry.buildMovieUri(id);
                }
                break;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case MOVIE:
                int count= database.delete(MovieContract.MovieEntry.TABLE_NAME, s, strings);
                return count;
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int count = 0;
        switch(uriMatcher.match(uri)) {
            case MOVIE:
                count = database.update(MovieContract.MovieEntry.TABLE_NAME, contentValues, null, null);
                break;
            case MOVIE_ITEM:
                count= updateMovie(uri, contentValues);
                break;
        }

        return count;
    }

    private Cursor findMovieById(Uri uri) {
        long id = MovieContract.MovieEntry.getMovieIdFromUri(uri);
        String query = new SqlExpressionBuilder().equals(MovieContract.MovieEntry.COLUMN_ID).build();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        return database.query(MovieContract.MovieEntry.TABLE_NAME,
                null, query, new String[] {String.valueOf(id)}, null, null, null, null);
    }

    private int updateMovie(Uri uri, ContentValues contentValues) {
        long id = MovieContract.MovieEntry.getMovieIdFromUri(uri);
        String query = new SqlExpressionBuilder().equals(MovieContract.MovieEntry.COLUMN_ID).build();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(MovieContract.MovieEntry.TABLE_NAME, contentValues, query, new String[]{String.valueOf(id)});
    }
}
