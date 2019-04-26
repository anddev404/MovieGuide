package com.anddev.movieguide.model;

import com.google.gson.Gson;

import java.util.List;

public class People {

    private String imdb_id;
    private boolean adult;
    private String profile_path;
    private String place_of_birth;
    private double popularity;
    private String biography;
    private int gender;
    private List<String> also_known_as;
    private String name;
    private int id;
    private String known_for_department;
    private String birthday;

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
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

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public static People getExamplePeople() {
        try {
            String jsonString = "{\n" +
                    "  \"birthday\": \"1967-07-26\",\n" +
                    "  \"known_for_department\": \"Acting\",\n" +
                    "  \"id\": 976,\n" +
                    "  \"name\": \"Jason Statham\",\n" +
                    "  \"also_known_as\": [\n" +
                    "    \"Джейсон Стейтем\",\n" +
                    "    \"Джейсон Стэйтем\",\n" +
                    "    \"جيسون ستاثام\",\n" +
                    "    \"제이슨 스테이섬\",\n" +
                    "    \"ジェイソン・ステイサム\",\n" +
                    "    \"เจสัน สเตธัม\",\n" +
                    "    \"傑森·史塔森\",\n" +
                    "    \"Джейсън Стейтъм\",\n" +
                    "    \"ჯეისონ სტეტჰემი\"\n" +
                    "  ],\n" +
                    "  \"gender\": 2,\n" +
                    "  \"biography\": \"\",\n" +
                    "  \"popularity\": 15.98,\n" +
                    "  \"place_of_birth\": \"Shirebrook, Derbyshire, England, UK\",\n" +
                    "  \"profile_path\": \"/PhWiWgasncGWD9LdbsGcmxkV4r.jpg\",\n" +
                    "  \"adult\": false,\n" +
                    "  \"imdb_id\": \"nm0005458\"\n" +
                    "}";
            Gson gson = new Gson();
            return gson.fromJson(jsonString, People.class);
        } catch (Exception e) {
            return null;
        }

    }
}
