package com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList;

/**
 * Created by Cesar on 11/03/2017.
 */

public class InterestItem {

    private String name;
    private boolean isChecked;

    public InterestItem(String name, boolean isChecked){
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
