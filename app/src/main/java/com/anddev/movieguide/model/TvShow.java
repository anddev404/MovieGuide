package com.anddev.movieguide.model;

import java.util.List;

public class TvShow {

    private double vote_average;
    private List<Production_companies> production_companies;
    private String poster_path;
    private String overview;
    private String original_name;
    private String name;
    private int id;
    private List<Genres> genres;
    private String first_air_date;
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

    public String productionCompaniesToString() {
        try {
            String productionCompaniesString = "";

            if (production_companies != null && production_companies.size() > 0) {
                productionCompaniesString = production_companies.get(0).getName();

                for (int i = 1; i < production_companies.size(); i++) {
                    productionCompaniesString = productionCompaniesString + ", " + production_companies.get(i).getName();
                }
            }
            return productionCompaniesString;


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

    public List<Production_companies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<Production_companies> production_companies) {
        this.production_companies = production_companies;
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

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

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

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public static class Production_companies {
        private String name;
        private String logo_path;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
