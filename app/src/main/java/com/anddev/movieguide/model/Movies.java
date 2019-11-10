package com.anddev.movieguide.model;

import java.util.List;

public class Movies {


    private List<ResultsMovie> results;
    private int total_pages;
    private int total_results;
    private int page;

    public List<ResultsMovie> getResults() {
        return results;
    }

    public void setResults(List<ResultsMovie> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static class Results {

    }
}
