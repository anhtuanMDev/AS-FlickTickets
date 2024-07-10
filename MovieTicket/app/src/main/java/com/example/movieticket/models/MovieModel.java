package com.example.movieticket.models;

import java.util.List;

public class MovieModel {
    private List<MinimumMovie> displayList;
    private List<DisplayMovie> recommendList;

    public MovieModel(List<MinimumMovie> displayList, List<DisplayMovie> recommendList) {
        this.displayList = displayList;
        this.recommendList = recommendList;
    }

    public List<MinimumMovie> getDisplay() {
        return displayList;
    }

    public void setDisplay(List<MinimumMovie> displayList) {
        this.displayList = displayList;
    }

    public List<DisplayMovie> getRecommend() {
        return recommendList;
    }

    public void setRecommend(List<DisplayMovie> recommendList) {
        this.recommendList = recommendList;
    }
}
