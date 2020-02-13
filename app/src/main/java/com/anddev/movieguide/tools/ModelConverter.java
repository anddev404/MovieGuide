package com.anddev.movieguide.tools;

import com.anddev.movieguide.model.Favourite;
import com.anddev.movieguide.model.Movies;
import com.anddev.movieguide.model.PopularPeople;
import com.anddev.movieguide.model.Results;
import com.anddev.movieguide.model.ResultsMovie;

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
                    r.setId(f.getId());
                    r.setTitle(f.getName() + "\n\n" + f.getDescription());
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

    public static PopularPeople favouritesToPopularPeople(List<Favourite> favourites, int type) {
        PopularPeople people = new PopularPeople();
        List<Results> results = new ArrayList<>();
        people.setResults(results);

        if (favourites != null) {
            for (Favourite f : favourites) {
                if (type == f.type) {
                    Results r = new Results();
                    r.setId(f.getId());
                    r.setName(f.getName());
                    results.add(r);
                }
            }
        }

        return people;
    }
}
