
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Comment implements Parcelable {

    @SerializedName("nick_user")
    @Expose
    private String nickUser;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("like")
    @Expose
    private Boolean like;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("date_feedback")
    @Expose
    private String dateFeedback;
    @SerializedName("pros")
    @Expose
    private ArrayList<String> pros = null;
    @SerializedName("cons")
    @Expose
    private ArrayList<String> cons = null;

    public Comment(){
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nickUser);
        out.writeString(date);
        out.writeString(description);
        out.writeInt(like ? 1 : 0);
        out.writeString(feedback);
        out.writeString(dateFeedback);
        out.writeList(pros);
        out.writeList(cons);
    }

    @SuppressWarnings("unchecked")
    private Comment(Parcel in) {
        nickUser = in.readString();
        date = in.readString();
        description = in.readString();
        like = (in.readInt() == 0) ? false : true;
        feedback = in.readString();
        dateFeedback = in.readString();
        pros = new ArrayList<String>();
        pros = in.readArrayList(Object.class.getClassLoader());
        cons = new ArrayList<String>();
        cons = in.readArrayList(Object.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Comment> CREATOR =
            new Creator<Comment>() {
                public Comment createFromParcel(Parcel in) {
                    return new Comment(in);
                }

                public Comment[] newArray(int size) {
                    return new Comment[size];
                }
            };

    public String getNickUser() {
        return nickUser;
    }

    public void setNickUser(String nickUser) {
        this.nickUser = nickUser;
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

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDateFeedback() {
        return dateFeedback;
    }

    public void setDateFeedback(String dateFeedback) {
        this.dateFeedback = dateFeedback;
    }

    public ArrayList<String> getPros() {
        return pros;
    }

    public void setPros(ArrayList<String> pros) {
        this.pros = pros;
    }

    public ArrayList<String> getCons() {
        return cons;
    }

    public void setCons(ArrayList<String> cons) {
        this.cons = cons;
    }

}
