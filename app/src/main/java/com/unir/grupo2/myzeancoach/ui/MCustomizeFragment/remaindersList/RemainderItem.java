package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList;

/**
 * Created by andres on 19/03/2017.
 */

public class RemainderItem {
    String title;
    String description;
    Boolean isCompleted;

    public RemainderItem(String title, String description, Boolean isCompleted) {

        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
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
