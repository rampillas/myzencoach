
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InterestListPojo {

    @SerializedName("results")
    @Expose
    private List<Interest> interests = null;

    public List<Interest> getInterests() {
        return interests;
    }

    public void setResults(List<Interest> interests) {
        this.interests = interests;
    }

}
