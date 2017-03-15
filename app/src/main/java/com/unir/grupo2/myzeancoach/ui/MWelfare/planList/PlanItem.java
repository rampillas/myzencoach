package com.unir.grupo2.myzeancoach.ui.MWelfare.planList;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class PlanItem {

    private String namePlan;
    private int week;

    public PlanItem(String namePlan, int week) {
        this.namePlan = namePlan;
        this.week = week;
    }

    public String getNamePlan() {
        return namePlan;
    }

    public void setNamePlan(String namePlan) {
        this.namePlan = namePlan;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
