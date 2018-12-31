package com.example.hp.featuredsongs.activities;

public class UserFollowers {
    private String name;
    private int thumbnail;

    public String getName() {
        return name;
    }

    public UserFollowers(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
