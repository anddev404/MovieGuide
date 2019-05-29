package com.anddev.movieguide.tools;

import com.anddev.movieguide.model.KnownFor;
import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Images;
import com.anddev.movieguide.model.PopularPeople;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {


    @GET("3/person/{id}")
    Call<Actor> dataOfPerson(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/person/976/movie_credits?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/person/{id}/movie_credits")
    Call<KnownFor> knownFor(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/person/976/images?api_key=3a3657f217097dc333bd92af0d39bee4
    @GET("3/person/{id}/images")
    Call<Images> images(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);


    //https://api.themoviedb.org/3/person/popular?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/person/popular")
    Call<PopularPeople> popularPeople(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);
}
