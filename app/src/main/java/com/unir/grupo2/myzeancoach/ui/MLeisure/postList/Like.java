package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.os.Parcel;
import android.os.Parcelable;

import com.unir.grupo2.myzeancoach.domain.model.User;

/**
 * Created by Cesar on 22/03/2017.
 */

public class Like implements Parcelable {

    private User user;


    public Like(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected Like(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Like> CREATOR = new Parcelable.Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel in) {
            return new Like(in);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };
}