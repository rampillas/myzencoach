
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable{

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_right")
    @Expose
    private Boolean isRight;

    public Answer(){
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(description);
        out.writeByte((byte) (isRight ? 1 : 0));
    }

    public static final Parcelable.Creator<Answer> CREATOR
            = new Parcelable.Creator<Answer>() {
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    private Answer(Parcel in) {
        description = in.readString();
        isRight = (in.readInt() == 0) ? false : true;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(Boolean isRight) {
        this.isRight = isRight;
    }

}
