package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

/**
 * Created by Cesar on 11/03/2017.
 */

public class PostItem {

    private String title;
    private String category;
    private String description;
    private int likeNumber;
    private int commentNumber;

    public PostItem(String title, String category, String description, int likeNumber, int commentNumber){
        this.title = title;
        this.category = category;
        this.description = description;
        this.likeNumber = likeNumber;
        this.commentNumber = commentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }
}
