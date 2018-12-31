package com.example.hp.featuredsongs.activities;

import java.util.Date;

public class PlaylistList {
    private String name;
    private String date;

    public String getName() {
        return name;
    }

    public PlaylistList(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
