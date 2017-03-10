
package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Video {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("new_attr")
    @Expose
    private Integer newAttr;
    @SerializedName("is_watched")
    @Expose
    private Boolean isWatched;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @SerializedName("survey")
    @Expose
    private List<Object> survey = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNewAttr() {
        return newAttr;
    }

    public void setNewAttr(Integer newAttr) {
        this.newAttr = newAttr;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean isWatched) {
        this.isWatched = isWatched;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Object> getSurvey() {
        return survey;
    }

    public void setSurvey(List<Object> survey) {
        this.survey = survey;
    }

}
