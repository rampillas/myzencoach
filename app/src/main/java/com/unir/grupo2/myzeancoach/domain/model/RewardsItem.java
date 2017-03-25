package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andres on 25/03/2017.
 */

public class RewardsItem {
    @SerializedName("points")
    @Expose
    private int points;

    @Override
    public String toString() {
        return "RewardsItem{" +
                "points=" + points +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

