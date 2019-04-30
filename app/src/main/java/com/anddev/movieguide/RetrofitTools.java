package com.anddev.movieguide;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTools {

    static final String API_BASE_URL = "https://api.themoviedb.org";
    static final String API_KEY = "3a3657f217097dc333bd92af0d39bee4";
    static final String LANGUAGE = "pl-PL";

    static ConnectionInterface getConnectionInterface() {

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
