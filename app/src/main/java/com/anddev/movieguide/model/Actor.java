package com.anddev.movieguide.model;

import com.google.gson.Gson;

public class Actor {

    private String profile_path;
    private String place_of_birth;
    private String biography;
    private String name;
    private int id;
    private String birthday;


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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public static Actor getExampleActor() {
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
            return gson.fromJson(jsonString, Actor.class);
        } catch (Exception e) {
            return null;
        }

    }
}
