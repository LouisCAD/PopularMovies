package xyz.louiscad.popularmovies.rest;

import android.content.Context;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.MovieDiscoverResult;
import xyz.louiscad.popularmovies.model.ReviewsResult;
import xyz.louiscad.popularmovies.model.VideosResult;
import xyz.louiscad.popularmovies.rest.converter.LoganSquareConverterFactory;

/**
 * API to access <a href="http://themoviedb.org">TheMovieDb</a>
 */
public final class TheMovieDb {

    public static final String API_ENDPOINT = "https://api.themoviedb.org/3/";

    public static API createAPI(Context context) {
        String apiKey = context.getString(R.string.TMDb_api_key);
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new AuthInterceptor(apiKey));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(new LoganSquareConverterFactory())
                .client(client)
                .build();
        return retrofit.create(API.class);
    }

    private static class AuthInterceptor implements Interceptor {

        private String mApiKey;

        public AuthInterceptor(String apiKey) {
            mApiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().httpUrl()
                    .newBuilder()
                    .addQueryParameter("api_key", mApiKey)
                    .build();
            Request request = chain.request().newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }

    private TheMovieDb() throws IllegalAccessException {
        throw new IllegalAccessException("This is a non instantiable utility class!");
    }

    public interface API {

        @GET("discover/movie?voteCount.gte=50")
        Call<MovieDiscoverResult> discoverMovies(@Query("page") int page, @Query("sort_by") String sort);

        @GET("movie/{id}?append_to_response=videos")
        Call<Movie> getMovieDetails(@Path("id") long id);

        @GET("movie/{id}/videos")
        Call<VideosResult> getVideos(@Path("id") long id);

        @GET("movie/{id}/review")
        Call<ReviewsResult> getReviews(@Path("id") long id);
    }
}
