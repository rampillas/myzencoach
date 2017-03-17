package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Cesar on 11/03/2017.
 */

public class DilemmaPost implements Parcelable{

    private String date;
    private String nick;
    private String title;
    private String description;
    private String state;
    private ArrayList<DilemmaComment> comments = null;

    public DilemmaPost(String date, String nick,String title, String description, String state, ArrayList<DilemmaComment> comments){
        this.date = date;
        this.nick = nick;
        this.title = title;
        this.description = description;
        this.state = state;
        this.comments = comments;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(nick);
        out.writeString(title);
        out.writeString(description);
        out.writeString(state);
        out.writeList(comments);
    }

    @SuppressWarnings("unchecked")
    private DilemmaPost(Parcel in) {
        date = in.readString();
        nick = in.readString();
        title = in.readString();
        description = in.readString();
        state = in.readString();
        comments = new ArrayList<DilemmaComment>();
        comments = in.readArrayList(DilemmaComment.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<DilemmaPost> CREATOR =
            new Creator<DilemmaPost>() {
                public DilemmaPost createFromParcel(Parcel in) {
                    return new DilemmaPost(in);
                }

                public DilemmaPost[] newArray(int size) {
                    return new DilemmaPost[size];
                }
            };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<DilemmaComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<DilemmaComment> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
