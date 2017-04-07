
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventListPojo {

    @SerializedName("results")
    @Expose
    private List<Event> results = null;

    public List<Event> getResults() {
        return results;
    }

    public void setResults(List<Event> results) {
        this.results = results;
    }

}
