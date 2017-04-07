
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ranking implements Parcelable{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("score")
    @Expose
    private Integer score;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(username);
        out.writeInt(score);
    }

    @SuppressWarnings("unchecked")
    private Ranking(Parcel in) {
        username = in.readString();
        score = in.readInt();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<Ranking> CREATOR =
            new Parcelable.Creator<Ranking>() {
                public Ranking createFromParcel(Parcel in) {
                    return new Ranking(in);
                }

                public Ranking[] newArray(int size) {
                    return new Ranking[size];
                }
            };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
