
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Interest implements Parcelable{

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("name")
    @Expose
    private String name;

    public Interest(){

    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(name);
    }

    @SuppressWarnings("unchecked")
    private Interest(Parcel in) {
        user = in.readString();
        name = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Interest> CREATOR =
            new Creator<Interest>() {
                public Interest createFromParcel(Parcel in) {
                    return new Interest(in);
                }

                public Interest[] newArray(int size) {
                    return new Interest[size];
                }
            };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
