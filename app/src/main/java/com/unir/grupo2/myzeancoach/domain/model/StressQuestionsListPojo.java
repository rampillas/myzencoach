package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andres on 28/03/2017.
 */

public class StressQuestionsListPojo {

    @SerializedName("next")
    @Expose
    private Object next;

    @SerializedName("previous")
    @Expose
    private Object previous;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<ResultsItem> results;

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getNext() {
        return next;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "RewardsListPojo{" +
                        "next = '" + next + '\'' +
                        ",previous = '" + previous + '\'' +
                        ",count = '" + count + '\'' +
                        ",results = '" + results + '\'' +
                        "}";
    }
}

