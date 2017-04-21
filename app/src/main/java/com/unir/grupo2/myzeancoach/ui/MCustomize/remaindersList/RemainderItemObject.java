package com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList;

/**
 * Created by andres on 19/03/2017.
 */

public class RemainderItemObject {
    String title;
    String description;
    Boolean isCompleted;
    Boolean isObservationsEnabled;
    String user;

    public RemainderItemObject(String title, String description, Boolean isCompleted, String user, Boolean isObservationsEnabled) {

        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.user = user;
        this.isObservationsEnabled = isObservationsEnabled;

    }

    public Boolean getObservationsEnabled() {
        return isObservationsEnabled;
    }

    public void setObservationsEnabled(Boolean observationsEnabled) {
        isObservationsEnabled = observationsEnabled;
    }


    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }


}
