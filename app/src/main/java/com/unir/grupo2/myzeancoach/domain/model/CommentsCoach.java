
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsCoach implements Parcelable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(description);

    }

    @SuppressWarnings("unchecked")
    private CommentsCoach(Parcel in) {
        date = in.readString();
        description = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<CommentsCoach> CREATOR =
            new Creator<CommentsCoach>() {
                public CommentsCoach createFromParcel(Parcel in) {
                    return new CommentsCoach(in);
                }

                public CommentsCoach[] newArray(int size) {
                    return new CommentsCoach[size];
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

}
