
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DilemmaListPojo {

    @SerializedName("results")
    @Expose
    private List<Dilemma> dilemmas = null;

    public List<Dilemma> getDilemmas() {
        return dilemmas;
    }

    public void setDilemmas(List<Dilemma> dilemmas) {
        this.dilemmas = dilemmas;
    }

}
