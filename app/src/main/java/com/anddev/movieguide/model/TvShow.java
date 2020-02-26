package com.anddev.movieguide.model;

import java.util.List;

public class TvShow {

    private int vote_count;
    private double vote_average;
    private String type;
    private String status;
    private List<Seasons> seasons;
    private List<Production_companies> production_companies;
    private String poster_path;
    private double popularity;
    private String overview;
    private String original_name;
    private String original_language;
    private List<String> origin_country;
    private int number_of_seasons;
    private int number_of_episodes;
    private List<Networks> networks;
    private String name;
    private Last_episode_to_air last_episode_to_air;
    private String last_air_date;
    private List<String> languages;
    private boolean in_production;
    private int id;
    private String homepage;
    private List<Genres> genres;
    private String first_air_date;
    private List<Integer> episode_run_time;
    private List<Created_by> created_by;
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

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
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

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
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

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public List<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Networks> networks) {
        this.networks = networks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Last_episode_to_air getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(Last_episode_to_air last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public boolean getIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
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

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public List<Created_by> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<Created_by> created_by) {
        this.created_by = created_by;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public static class Seasons {
        private int season_number;
        private String poster_path;
        private String overview;
        private String name;
        private int id;
        private int episode_count;
        private String air_date;

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
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

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }
    }

    public static class Production_companies {
        private String origin_country;
        private String name;
        private String logo_path;
        private int id;

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
        }

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

    public static class Networks {
        private String origin_country;
        private String logo_path;
        private int id;
        private String name;

        public String getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(String origin_country) {
            this.origin_country = origin_country;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Last_episode_to_air {
        private int vote_count;
        private double vote_average;
        private String still_path;
        private int show_id;
        private int season_number;
        private String production_code;
        private String overview;
        private String name;
        private int id;
        private int episode_number;
        private String air_date;

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getStill_path() {
            return still_path;
        }

        public void setStill_path(String still_path) {
            this.still_path = still_path;
        }

        public int getShow_id() {
            return show_id;
        }

        public void setShow_id(int show_id) {
            this.show_id = show_id;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }

        public String getProduction_code() {
            return production_code;
        }

        public void setProduction_code(String production_code) {
            this.production_code = production_code;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
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

        public int getEpisode_number() {
            return episode_number;
        }

        public void setEpisode_number(int episode_number) {
            this.episode_number = episode_number;
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
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

    public static class Created_by {
        private String profile_path;
        private int gender;
        private String name;
        private String credit_id;
        private int id;

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
