package com.example.lukaszwachowski.popularmovies.network.reviews;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {

    @SerializedName("id")
    private Integer id;

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<ReviewsResult> results = null;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getId() {
        return id;
    }

    public Integer getPage() {
        return page;
    }

    public List<ReviewsResult> getResults() {
        return results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }
}
