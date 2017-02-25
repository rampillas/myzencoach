package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList;

/**
 * Created by Cesar on 24/02/2017.
 */

//Provisional class until services are ready
public class TestItem {

    private String videoName;
    private boolean completed;
    private int score;

    public TestItem(String videoName, boolean completed, int score){
        this.videoName = videoName;
        this.completed = completed;
        this.score = score;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
