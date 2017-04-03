package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cesar on 03/04/2017.
 */

public class Pro implements Parcelable {

    @SerializedName("description")
    @Expose
    private String description;

    public Pro(){
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(description);
    }

    @SuppressWarnings("unchecked")
    private Pro(Parcel in) {
        description = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Pro> CREATOR =
            new Creator<Pro>() {
                public Pro createFromParcel(Parcel in) {
                    return new Pro(in);
                }

                public Pro[] newArray(int size) {
                    return new Pro[size];
                }
            };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
