package com.dupleit.kotlin.primaryschoolassessment.Network;

/**
 * Created by mandeep on 6/7/17.
 */


import com.dupleit.kotlin.primaryschoolassessment.otherHelper.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = Utils.webUrl;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        // set your desired log level

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        logging.setLevel(Level.BODY);
        // add your other interceptors …

        // add logging as last interceptor

        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}