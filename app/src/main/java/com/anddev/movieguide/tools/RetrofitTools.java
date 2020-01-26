package com.anddev.movieguide.tools;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTools {

    public static final String API_BASE_URL = "https://api.themoviedb.org";
    public static final String API_KEY = "3a3657f217097dc333bd92af0d39bee4";
    public static final Integer EXAMPLE_ID_MOVIE = 338967;


    public static ConnectionInterface getConnectionInterface() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(RetrofitTools.API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        return retrofit.create(ConnectionInterface.class);

    }


}
