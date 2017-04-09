package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentEvent implements Parcelable{

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(date);
        out.writeString(description);
    }

    @SuppressWarnings("unchecked")
    private CommentEvent(Parcel in) {
        user = in.readString();
        date = in.readString();
        description = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<CommentEvent> CREATOR =
            new Creator<CommentEvent>() {
                public CommentEvent createFromParcel(Parcel in) {
                    return new CommentEvent(in);
                }

                public CommentEvent[] newArray(int size) {
                    return new CommentEvent[size];
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
