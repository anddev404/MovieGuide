package com.anddev.movieguide.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Favourite implements Serializable {

    public static final int FAVOURITE_MOVIE = 1;
    public static final int FAVOURITE_ACTOR = 2;
    public static final int FAVOURITE_TV_SHOWS = 3;

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public int favouriteId;

    @DatabaseField
    public int type;

    @DatabaseField
    public String name;

    @DatabaseField
    public String description;

    @DatabaseField
    public String rating;

    @DatabaseField
    public String posterPath;

    public Favourite() {
    }

    public Favourite(int favouriteId, int type, String name, String description, String rating, String posterPath) {
        this.favouriteId = favouriteId;
        this.type = type;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.posterPath = posterPath;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
