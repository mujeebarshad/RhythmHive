package com.example.hp.featuredsongs.activities;

public class FeaturedSong {
    private String name;
    private double duration;
    private int thumbnail;

    public FeaturedSong() {
    }

    public FeaturedSong(String name, double duration, int thumbnail) {
        this.name = name;
        this.duration = duration;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDurations(double duration) {
        this.duration = duration;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}