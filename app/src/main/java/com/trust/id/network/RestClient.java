package com.trust.id.network;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    //private static final String BASE_URL = "http://35.193.102.158/";
    private static final String BASE_URL = "https://api.autentia.id/profile/";
    private static final int CONNECT_TIMEOUT = 30 * 4;
    private static final int WRITE_TIMEOUT = 30 * 4;
    private static final int READ_TIMEOUT = 30 * 4;
    private static APIUsers api;

    static {
        setup();
    }

    /**
     * Class constructor
     */
    private RestClient() {
    }

    /**
     * Obtain the access of all APIUsers methods
     *
     * @return APIUsers reference
     */
    public static APIUsers get() {
        return api;
    }

    /**
     * Default setup for OkHttp and Retrofit
     */
    private static void setup() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                //.addInterceptor(new HeaderInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .client(okHttpClient)
                .build();

        api = retrofit.create(APIUsers.class);
    }
}