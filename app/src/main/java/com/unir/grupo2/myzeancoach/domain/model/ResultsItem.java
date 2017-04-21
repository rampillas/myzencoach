package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ResultsItem {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("active")
    @Expose
    private int active;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "description = '" + description + '\'' +
                        ",active = '" + active + '\'' +
                        "}";
    }
}