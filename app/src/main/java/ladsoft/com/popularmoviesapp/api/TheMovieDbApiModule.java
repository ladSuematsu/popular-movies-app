package ladsoft.com.popularmoviesapp.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ladsoft.com.popularmoviesapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbApiModule {
    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    public static Gson providesGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
    }

    public static TheMovieDbApi providesApiAdapter() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(providesGson());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();

        return retrofit.create(TheMovieDbApi.class);
    }
}
