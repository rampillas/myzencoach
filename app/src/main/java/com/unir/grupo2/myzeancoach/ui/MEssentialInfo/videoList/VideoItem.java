package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.videoList;

import android.graphics.drawable.Drawable;

/**
 * Created by Cesar on 28/02/2017.
 */

public class VideoItem {

    private Drawable urlImage;
    private boolean isCompleted;
    private String urlVideo;

    public VideoItem(Drawable urlImage, boolean isCompleted, String urlVideo){
        this.urlImage = urlImage;
        this.isCompleted = isCompleted;
        this.urlVideo = urlVideo;
    }

    public Drawable getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(Drawable urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
