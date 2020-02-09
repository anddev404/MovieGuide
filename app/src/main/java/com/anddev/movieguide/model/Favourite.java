package com.anddev.movieguide.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Favourite implements Serializable {

    static final int FAVOURITE_MOVIE = 1;
    static final int FAVOURITE_ACTOR = 2;

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public int favouriteId;

    @DatabaseField
    public int type;

    public Favourite() {
    }

    public Favourite(int favouriteId, int type) {
        this.favouriteId = favouriteId;
        this.type = type;
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

    public static List<Favourite> getExampleFavouriteMovie() {


        List<Favourite> dane = new ArrayList<>();

        dane.add(new Favourite(234534, 1));
        dane.add(new Favourite(678234, 1));

        return dane;

    }

    public static List<Favourite> getExampleFavouritePeople() {


        List<Favourite> dane = new ArrayList<>();

        dane.add(new Favourite(3453, 1));
        dane.add(new Favourite(2345, 1));

        return dane;

    }

}
