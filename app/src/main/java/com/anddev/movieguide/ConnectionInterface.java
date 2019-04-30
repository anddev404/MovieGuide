package com.anddev.movieguide;

import com.anddev.movieguide.model.Actor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {


    @GET("3/person/{id}")
    Call<Actor> dataOfPerson(@Path("id") Integer id,@Query("api_key") String apiKey,@Query("language") String language);

}
