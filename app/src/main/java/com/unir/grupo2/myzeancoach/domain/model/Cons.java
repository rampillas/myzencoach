package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cesar on 03/04/2017.
 */

public class Cons implements Parcelable {

    @SerializedName("description")
    @Expose
    private String description;

    public Cons(){
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(description);
    }

    @SuppressWarnings("unchecked")
    private Cons(Parcel in) {
        description = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Cons> CREATOR =
            new Creator<Cons>() {
                public Cons createFromParcel(Parcel in) {
                    return new Cons(in);
                }

                public Cons[] newArray(int size) {
                    return new Cons[size];
                }
            };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
