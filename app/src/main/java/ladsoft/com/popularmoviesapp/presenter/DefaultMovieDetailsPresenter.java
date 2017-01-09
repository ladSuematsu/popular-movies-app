package ladsoft.com.popularmoviesapp.presenter;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import ladsoft.com.popularmoviesapp.BuildConfig;
import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApi;
import ladsoft.com.popularmoviesapp.api.TheMovieDbApiModule;
import ladsoft.com.popularmoviesapp.api.parser.MovieVideosRequestResult;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.model.MovieVideo;
import retrofit2.Call;
import retrofit2.Response;

public class DefaultMovieDetailsPresenter implements MovieDetailsPresenter<Movie> {
    private static final String TAG = DefaultMovieDetailsPresenter.class.getSimpleName();
    private static final String apiKey = BuildConfig.API_KEY;

    private final TheMovieDbApi api;
    private Movie movie;
    private Callback presenterCallback;
    private MovieVideosRequestResult movieVideos;

    public DefaultMovieDetailsPresenter(Callback presenterCallback) {
        this.presenterCallback = presenterCallback;
        this.api = TheMovieDbApiModule.providesApiAdapter();
    }

    @Override
    public void loadData(Movie movie) {
        this.movie = movie;
        presenterCallback.onDataLoaded(movie);
    }

    @Override
    public void loadMovieVideos() {
        Call<MovieVideosRequestResult> call = api.getMovieVideos(movie.getId(), apiKey);
        call.enqueue(callback);
    }

    @Override
    public void onMovieVideoSelected(Context context, MovieVideo video) {
        Uri videoUri = null;

        try {
            String linkFormat = context.getString(R.string.youtube_video_link_format);
            String videoLink = String.format(linkFormat, video.getKey());
            videoUri = Uri.parse(videoLink);
        } catch (NullPointerException e){
            Log.e(TAG, "Invalid key value", e);
        }

        if (videoUri != null) {
            presenterCallback.onVideoLaunch(videoUri);
        } else {
            presenterCallback.onError(ErrorType.VIDEO_LINK_PARSE_ERROR);
        }
    }

    @Override
    public String getPosterPath() {
        return movie.getPosterPath();
    }

    @Override
    public void setFavorite() {
        presenterCallback.onFavoriteSuccessful();
    }

    private retrofit2.Callback<MovieVideosRequestResult> callback = new retrofit2.Callback<MovieVideosRequestResult>() {
        @Override
        public void onResponse(Call<MovieVideosRequestResult> call, Response<MovieVideosRequestResult> response) {
            movieVideos = response.body();
            if(movieVideos != null) {
                presenterCallback.onVideoListLoaded(movieVideos.getResults());
            } else {
                presenterCallback.onError(MovieDetailsPresenter.ErrorType.VIDEO_DATA_LOAD_ERROR);
            }
        }

        @Override
        public void onFailure(Call<MovieVideosRequestResult> call, Throwable t) {
            Log.e(TAG, t.getLocalizedMessage(), t);
            presenterCallback.onError(MovieDetailsPresenter.ErrorType.DATA_LOAD_ERROR);
        }
    };
}
