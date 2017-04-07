
package com.unir.grupo2.myzeancoach.domain.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestListPojo {

    @SerializedName("results")
    @Expose
    private List<Interest> results = null;

    public List<Interest> getResults() {
        return results;
    }

    public void setResults(List<Interest> results) {
        this.results = results;
    }

}
