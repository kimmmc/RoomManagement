package com.example.roommanagement.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.roommanagement.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    public static ApiService getService(Context context) {
        if (retrofit == null) {
            // Setup Cache
            File httpCacheDirectory = new File(context.getCacheDir(), "http-cache");
            Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);

            // Build OkHttpClient
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(offlineInterceptor(context))
                    .addNetworkInterceptor(onlineInterceptor())
                    // Optional: Timeouts
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Build Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    /**
     * Interceptor to handle offline capability.
     * If no network, force reading from cache for up to 7 days.
     */
    private static Interceptor offlineInterceptor(Context context) {
        return chain -> {
            Request request = chain.request();
            if (!isNetworkAvailable(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .removeHeader("Pragma") // Pragma: no-cache can interfere
                        .build();
            }
            return chain.proceed(request);
        };
    }

    /**
     * Interceptor to set cache validity when online.
     * Cache for 1 minute to reduce network load.
     */
    private static Interceptor onlineInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES) // Cache for 1 min
                    .build();

            return response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .removeHeader("Pragma")
                    .build();
        };
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
