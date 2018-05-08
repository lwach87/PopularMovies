package com.example.lukaszwachowski.popularmovies.network.videos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos {

    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private List<VideosResult> results = null;

    public Integer getId() {
        return id;
    }

    public List<VideosResult> getResults() {
        return results;
    }
}
