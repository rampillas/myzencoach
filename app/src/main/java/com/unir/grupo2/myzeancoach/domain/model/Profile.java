
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rural_zone")
    @Expose
    private String ruralZone;
    @SerializedName("change_country")
    @Expose
    private String changeCountry;
    @SerializedName("level_studies")
    @Expose
    private String levelStudies;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuralZone() {
        return ruralZone;
    }

    public void setRuralZone(String ruralZone) {
        this.ruralZone = ruralZone;
    }

    public String getChangeCountry() {
        return changeCountry;
    }

    public void setChangeCountry(String changeCountry) {
        this.changeCountry = changeCountry;
    }

    public String getLevelStudies() {
        return levelStudies;
    }

    public void setLevelStudies(String levelStudies) {
        this.levelStudies = levelStudies;
    }

}
