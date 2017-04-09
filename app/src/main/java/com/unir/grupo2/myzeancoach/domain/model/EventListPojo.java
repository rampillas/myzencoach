
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventListPojo {

    @SerializedName("results")
    @Expose
    private List<Event> events = null;

    public List<Event> getEvents() {
        return events;
    }

    public void setResults(List<Event> events) {
        this.events = events;
    }

}
