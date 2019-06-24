package com.anddev.movieguide.model;

import java.util.List;

public class Results {
    private boolean adult;
    private List<KnownForPopular> known_for;
    private String name;
    private String profile_path;
    private int id;
    private double popularity;

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public List<KnownForPopular> getKnownForPopular() {
        return known_for;
    }

    public void setKnownForPopular(List<KnownForPopular> known_for) {
        this.known_for = known_for;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
