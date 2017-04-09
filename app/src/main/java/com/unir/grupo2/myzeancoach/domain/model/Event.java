
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Event implements Parcelable{

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("user_like")
    @Expose
    private UserLike userLike;
    @SerializedName("comments")
    @Expose
    private List<CommentEvent> comments = null;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(date);
        out.writeString(title);
        out.writeString(description);
        out.writeInt(likes);
        out.writeString(category);
        out.writeValue(userLike);
        out.writeList(comments);
    }

    @SuppressWarnings("unchecked")
    private Event(Parcel in) {
        user = in.readString();
        date = in.readString();
        title = in.readString();
        description = in.readString();
        likes = in.readInt();
        category = in.readString();
        userLike = (UserLike) in.readValue(UserLike.class.getClassLoader());
        comments = new ArrayList<CommentEvent>();
        comments = in.readArrayList(CommentEvent.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Event> CREATOR =
            new Creator<Event>() {
                public Event createFromParcel(Parcel in) {
                    return new Event(in);
                }

                public Event[] newArray(int size) {
                    return new Event[size];
                }
            };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserLike getUserLike() {
        return userLike;
    }

    public void setUserLike(UserLike userLike) {
        this.userLike = userLike;
    }

    public List<CommentEvent> getComments() {
        return comments;
    }

    public void setComments(List<CommentEvent> comments) {
        this.comments = comments;
    }

}
