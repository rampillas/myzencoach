
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Dilemma implements Parcelable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("nick_user")
    @Expose
    private String nickUser;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("comments")
    @Expose
    private ArrayList<Comment> comments = null;
    @SerializedName("comments_coach")
    @Expose
    private ArrayList<CommentsCoach> commentsCoach = null;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(title);
        out.writeString(description);
        out.writeString(nickUser);
        out.writeString(type);
        out.writeString(state);
        out.writeList(comments);
        out.writeList(commentsCoach);
    }

    @SuppressWarnings("unchecked")
    private Dilemma(Parcel in) {
        date = in.readString();
        title = in.readString();
        description = in.readString();
        nickUser = in.readString();
        type = in.readString();
        state = in.readString();
        comments = new ArrayList<Comment>();
        comments = in.readArrayList(Comment.class.getClassLoader());
        commentsCoach = new ArrayList<CommentsCoach>();
        commentsCoach = in.readArrayList(CommentsCoach.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Dilemma> CREATOR =
            new Creator<Dilemma>() {
                public Dilemma createFromParcel(Parcel in) {
                    return new Dilemma(in);
                }

                public Dilemma[] newArray(int size) {
                    return new Dilemma[size];
                }
            };

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

    public String getNickUser() {
        return nickUser;
    }

    public void setNickUser(String nickUser) {
        this.nickUser = nickUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<CommentsCoach> getCommentsCoach() {
        return commentsCoach;
    }

    public void setCommentsCoach(ArrayList<CommentsCoach> commentsCoach) {
        this.commentsCoach = commentsCoach;
    }

}
