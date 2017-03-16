package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Cesar on 16/03/2017.
 */

public class DilemmaComment implements Parcelable {

    private String date;
    private String description;
    private ArrayList<String> pros;
    private ArrayList<String> cons;
    private boolean isLike;

    public DilemmaComment(String date, String description, ArrayList<String> pros, ArrayList<String> cons, boolean isLike){
        this.date = date;
        this.description = description;
        this.pros = pros;
        this.cons = cons;
        this.isLike = isLike;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(description);
        out.writeList(pros);
        out.writeList(cons);
        out.writeInt(isLike ? 1 : 0);
    }

    @SuppressWarnings("unchecked")
    private DilemmaComment(Parcel in) {
        date = in.readString();
        description = in.readString();
        pros = new ArrayList<String>();
        pros = in.readArrayList(String.class.getClassLoader());
        cons = new ArrayList<String>();
        cons = in.readArrayList(String.class.getClassLoader());
        isLike = (in.readInt() == 0) ? false : true;
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<DilemmaComment> CREATOR =
            new Creator<DilemmaComment>() {
                public DilemmaComment createFromParcel(Parcel in) {
                    return new DilemmaComment(in);
                }

                public DilemmaComment[] newArray(int size) {
                    return new DilemmaComment[size];
                }
            };

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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
