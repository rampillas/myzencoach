package com.unir.grupo2.myzeancoach.ui.MLeisure.commentList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cesar on 12/03/2017.
 */

public class CommentItem implements Parcelable{

    private String date;
    private String description;

    public CommentItem(String date, String description){
        this.date = date;
        this.description = description;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(description);
    }

    @SuppressWarnings("unchecked")
    private CommentItem(Parcel in) {
        date = in.readString();
        description = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<CommentItem> CREATOR =
            new Parcelable.Creator<CommentItem>() {
                public CommentItem createFromParcel(Parcel in) {
                    return new CommentItem(in);
                }

                public CommentItem[] newArray(int size) {
                    return new CommentItem[size];
                }
            };

    public int describeContents() {
        return this.hashCode();
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
