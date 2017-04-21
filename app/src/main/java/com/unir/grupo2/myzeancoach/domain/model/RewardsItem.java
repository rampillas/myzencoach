package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RewardsItem {

    @SerializedName("points")
    @Expose
    private int points;

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return
                "RewardsItem{" +
                        "points = '" + points + '\'' +
                        "}";
    }
}