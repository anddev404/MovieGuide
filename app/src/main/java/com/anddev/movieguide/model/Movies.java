package com.anddev.movieguide.model;

import java.util.List;

public class Movies {


    private List<ResultsMovie> results;

    public List<ResultsMovie> getResults() {
        return results;
    }

    public void setResults(List<ResultsMovie> results) {
        this.results = results;
    }

    public static class Results {

    }
}
