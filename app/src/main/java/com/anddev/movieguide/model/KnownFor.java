package com.anddev.movieguide.model;

import java.util.ArrayList;
import java.util.List;

public class KnownFor {


    private int id;
    private List<Cast> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public static KnownFor getExampleKnownFor() {

        KnownFor knownFor = new KnownFor();

        Cast cast = new Cast();
        Cast cast2 = new Cast();

        cast.setOriginal_title("Lock, Stock and Two Smoking Barrels");
        cast2.setOriginal_title("Ghosts of Mars");

        List<Cast> list = new ArrayList<>();
        list.add(cast);
        list.add(cast2);

        knownFor.setCast(list);
        return knownFor;
    }
}

