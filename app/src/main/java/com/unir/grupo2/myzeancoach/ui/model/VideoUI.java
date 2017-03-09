package com.unir.grupo2.myzeancoach.ui.model;

import java.util.List;

/**
 * Created by Cesar on 09/03/2017.
 */

public class VideoUI {

    private String title;
    private String urlVideo;
    private Boolean isWatched;
    private String date;
    private String coverUrl;
    private List<Object> survey = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean watched) {
        isWatched = watched;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<Object> getSurvey() {
        return survey;
    }

    public void setSurvey(List<Object> survey) {
        this.survey = survey;
    }
}
