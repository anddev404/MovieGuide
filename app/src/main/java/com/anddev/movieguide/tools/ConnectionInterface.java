package com.anddev.movieguide.tools;

import com.anddev.movieguide.model.Actor;
import com.anddev.movieguide.model.Credits;
import com.anddev.movieguide.model.Genre;
import com.anddev.movieguide.model.Images;
import com.anddev.movieguide.model.KnownFor;
import com.anddev.movieguide.model.Movie;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.TvShow;
import com.anddev.movieguide.model.TvShows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {

    //PERSON

    @GET("3/person/{id}")
    Call<Actor> dataOfPerson(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/person/976/movie_credits?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/person/{id}/movie_credits")
    Call<KnownFor> knownFor(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //    https://api.themoviedb.org/3/person/{person_id}/combined_credits?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/person/{id}/combined_credits")
    Call<KnownFor> knownForCombined(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/person/976/images?api_key=3a3657f217097dc333bd92af0d39bee4
    @GET("3/person/{id}/images")
    Call<Images> images(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);


    //https://api.themoviedb.org/3/person/popular?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/person/popular")
    Call<PopularPeople> popularPeople(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);


    //MOVIE

    //https://api.themoviedb.org/3/movie/338967?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/movie/{id}")
    Call<Movie> movie(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/movie/popular?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/movie/popular")
    Call<Movies> popularMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/movie/top_rated?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/movie/top_rated")
    Call<Movies> topRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/movie/now_playing?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/movie/now_playing")
    Call<Movies> nowPlayingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/movie/upcoming?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/movie/upcoming")
    Call<Movies> upcomingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);


    //https://api.themoviedb.org/3/movie/338967/credits?api_key=3a3657f217097dc333bd92af0d39bee4
    @GET("3/movie/{id}/credits")
    Call<Credits> credits(@Path("id") Integer id, @Query("api_key") String apiKey);

    //https://api.themoviedb.org/3/movie/338967/similar?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/movie/{id}/similar")
    Call<Movies> similarMovies(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/genre/movie/list?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/genre/movie/list")
    Call<Genre> genres(@Query("api_key") String apiKey, @Query("language") String language);

    //TV SHOWS

    //https://api.themoviedb.org/3/tv/popular?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/tv/popular")
    Call<TvShows> popularTvShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/tv/top_rated?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/tv/top_rated")
    Call<TvShows> topRatedTvShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/tv/on_the_air?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/tv/on_the_air")
    Call<TvShows> onTheAirTvShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/tv/airing_today?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/tv/airing_today")
    Call<TvShows> airingTodayTvShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //https://api.themoviedb.org/3/tv/1418?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US
    @GET("3/tv/{id}")
    Call<TvShow> tvShow(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language);

    //https://api.themoviedb.org/3/tv/338/credits?api_key=3a3657f217097dc333bd92af0d39bee4
    @GET("3/tv/{id}/credits")
    Call<Credits> tvShowCredits(@Path("id") Integer id, @Query("api_key") String apiKey);

    //https://api.themoviedb.org/3/tv/{tv_id}/similar?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&page=1
    @GET("3/tv/{id}/similar")
    Call<TvShows> similarTvShows(@Path("id") Integer id, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") Integer page);

    //SEARCH

    //https://api.themoviedb.org/3/search/movie?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&query=terminator&page=1&include_adult=false
    @GET("3/search/movie")
    Call<Movies> searchMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("page") Integer page);

    //https://api.themoviedb.org/3/search/tv?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&query=big%20bang&page=1
    @GET("3/search/tv")
    Call<TvShows> searchTvShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("page") Integer page);

    //https://api.themoviedb.org/3/search/person?api_key=3a3657f217097dc333bd92af0d39bee4&language=en-US&query=naomi%20scott&page=1&include_adult=false
    @GET("3/search/person")
    Call<PopularPeople> searchPerson(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("page") Integer page);

}
