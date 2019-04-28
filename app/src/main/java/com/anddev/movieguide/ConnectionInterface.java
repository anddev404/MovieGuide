package com.anddev.movieguide;

import com.anddev.movieguide.model.Actor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {
//
//    //String API_BASE_URL = "https://api.themoviedb.org/3/person/";
////    String API_BASE_URL = "http://api.themoviedb.org/3/person/";
//
//    //https://api.themoviedb.org/3/person/976?api_key=3a3657f217097dc333bd92af0d39bee4&language=pl-PL
//    @GET("/actorId")
//    Call<Actor> reposForUser(@Path("actorId") Integer actorId, @Query("api_key") String api_keys, @Query("language") String languages);

//    @GET("3/person/976?api_key=3a3657f217097dc333bd92af0d39bee4&language=pl-PL")


    @GET("3/person/{id}")
   // lub tak mozna ze znakiem zapytania jak by yly jakies inne parametry stałe @GET("3/person/976?")
    Call<Actor> reposForUser(@Path("id") Integer byleccco,@Query("api_key") String byleCo,@Query("language") String byleCos);
//parametry moga byc nie pokoleji wiec nie musza byc w  @GET("3/person/976") pisane a sciezka musi byc dokladna wiec  musze podac w ktorym miejscu-
// -konkretnie w adresie np dla person musi byc tak  @GET("3/person/976") a nie tak  @GET("3//976")

}
