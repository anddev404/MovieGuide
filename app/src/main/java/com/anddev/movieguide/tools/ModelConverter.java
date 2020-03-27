package com.anddev.movieguide.tools;

import com.anddev.movieguide.model.Cast;
import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.model.ResultsMovie;
import com.anddev.movieguide.model.TvShows;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

    public static Movies favouritesToMovies(List<Favourite> favourites, int type) {
        Movies movies = new Movies();
        List<ResultsMovie> results = new ArrayList<>();
        movies.setResults(results);

        if (favourites != null) {
            for (Favourite f : favourites) {
                if (type == f.type) {
                    ResultsMovie r = new ResultsMovie();
                    r.setId(f.getFavouriteId());
                    r.setTitle(f.getName());
                    r.setPoster_path(f.getPosterPath());

                    Double doubleString;
                    try {
                        doubleString = Double.parseDouble(f.getRating());
                    } catch (Exception e) {
                        doubleString = 0d;
                    }
                    r.setVote_average(doubleString);
                    results.add(r);
                }
            }
        }

        return movies;
    }

    public static TvShows favouritesToTvShows(List<Favourite> favourites, int type) {
        TvShows tvShows = new TvShows();
        List<TvShows.Results> results = new ArrayList<>();
        tvShows.setResults(results);

        if (favourites != null) {
            for (Favourite f : favourites) {
                if (type == f.type) {
                    TvShows.Results r = new TvShows.Results();
                    r.setId(f.getFavouriteId());
                    r.setName(f.getName());
                    r.setPoster_path(f.getPosterPath());

                    Double doubleString;
                    try {
                        doubleString = Double.parseDouble(f.getRating());
                    } catch (Exception e) {
                        doubleString = 0d;
                    }
                    r.setVote_average(doubleString);
                    results.add(r);
                }
            }
        }

        return tvShows;
    }

    public static PopularPeople favouritesToPopularPeople(List<Favourite> favourites, int type) {
        PopularPeople people = new PopularPeople();
        List<Results> results = new ArrayList<>();
        people.setResults(results);

        if (favourites != null) {
            for (Favourite f : favourites) {
                if (type == f.type) {
                    Results r = new Results();
                    r.setId(f.getFavouriteId());
                    r.setName(f.getName());
                    r.setProfile_path(f.getPosterPath());
                    results.add(r);
                }
            }
        }

        return people;
    }

    public static List<Cast> moviesToListOfCast(Movies movies) {

        List<Cast> results = new ArrayList<>();

        try {
            if (movies != null && movies.getResults() != null) {
                for (ResultsMovie r : movies.getResults()) {

                    Cast cast = new Cast();
                    cast.setId(r.getId());
                    cast.setTitle(r.getTitle());
                    cast.setPoster_path(r.getPoster_path());
                    cast.setMedia_type("movie");
                    results.add(cast);
                }
            }
        } catch (Exception e) {
        }

        return results;
    }

    public static List<Cast> tvShowsToListOfCast(TvShows tvShows) {

        List<Cast> results = new ArrayList<>();

        try {
            if (tvShows != null && tvShows.getResults() != null) {
                for (TvShows.Results r : tvShows.getResults()) {

                    Cast cast = new Cast();
                    cast.setId(r.getId());
                    cast.setTitle(r.getName());
                    cast.setPoster_path(r.getPoster_path());
                    cast.setMedia_type("tv");
                    results.add(cast);
                }
            }
        } catch (Exception e) {
        }

        return results;
    }
}
