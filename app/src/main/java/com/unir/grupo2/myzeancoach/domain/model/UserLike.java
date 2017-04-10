
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLike implements Parcelable{

    @SerializedName("is_liked")
    @Expose
    private Boolean isLiked;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("event")
    @Expose
    private String event;

    public UserLike(){
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(isLiked ? 1 : 0);
        out.writeString(user);
        out.writeString(event);
    }

    @SuppressWarnings("unchecked")
    private UserLike(Parcel in) {
        isLiked = (in.readInt() == 0) ? false : true;
        user = in.readString();
        event = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<UserLike> CREATOR =
            new Creator<UserLike>() {
                public UserLike createFromParcel(Parcel in) {
                    return new UserLike(in);
                }

                public UserLike[] newArray(int size) {
                    return new UserLike[size];
                }
            };

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
