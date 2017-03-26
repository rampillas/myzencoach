package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.os.Parcel;
import android.os.Parcelable;

import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;

import java.util.ArrayList;

/**
 * Created by Cesar on 11/03/2017.
 */

public class EventItem implements Parcelable{

    private String date;
    private String title;
    private String category;
    private String description;
    private ArrayList<Like> likes;
    private ArrayList<CommentItem> comments;

    public EventItem(String date, String title, String category, String description, ArrayList<Like> likes, ArrayList<CommentItem> comments){
        this.date = date;
        this.title = title;
        this.category = category;
        this.description = description;
        this.likes = likes;
        this.comments = comments;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(title);
        out.writeString(category);
        out.writeString(description);
        out.writeList(likes);
        out.writeList(comments);

    }

    @SuppressWarnings("unchecked")
    private EventItem(Parcel in) {
        date = in.readString();
        title = in.readString();
        category = in.readString();
        description = in.readString();
        likes = new ArrayList<Like>();
        likes = in.readArrayList(Like.class.getClassLoader());
        comments = new ArrayList<CommentItem>();
        comments = in.readArrayList(CommentItem.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<EventItem> CREATOR =
            new Parcelable.Creator<EventItem>() {
                public EventItem createFromParcel(Parcel in) {
                    return new EventItem(in);
                }

                public EventItem[] newArray(int size) {
                    return new EventItem[size];
                }
            };

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

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }
}
