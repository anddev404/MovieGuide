package com.anddev.movieguide.model;

import java.util.List;

public class Movie {

    private double vote_average;
    private String title;
    private int runtime;
    private String release_date;
    private List<Production_countries> production_countries;
    private String poster_path;
    private String overview;
    private String original_title;
    private int id;
    private List<Genres> genres;
    private String backdrop_path;

    public String genresToString() {
        try {
            String genresString = "";

            if (genres != null && genres.size() > 0) {
                genresString = genres.get(0).getName();

                for (int i = 1; i < genres.size(); i++) {
                    genresString = genresString + ", " + genres.get(i).getName();
                }
            }
            return genresString;


        } catch (Exception e) {
            return "";
        }

    }

    public String productionCountriesToString() {
        try {
            String productionCountriesString = "";

            if (production_countries != null && production_countries.size() > 0) {
                productionCountriesString = production_countries.get(0).getName();

                for (int i = 1; i < production_countries.size(); i++) {
                    productionCountriesString = productionCountriesString + ", " + production_countries.get(i).getName();
                }
            }
            return productionCountriesString;


        } catch (Exception e) {
            return "";
        }

    }


    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Production_countries> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<Production_countries> production_countries) {
        this.production_countries = production_countries;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public static class Production_countries {
        private String name;
        private String iso_3166_1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }
    }

    public static class Genres {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
