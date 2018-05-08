package com.example.lukaszwachowski.popularmovies.network.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<MoviesResult> results = null;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<MoviesResult> getResults() {
        return results;
    }
}
