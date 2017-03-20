
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanWelfare implements Parcelable{

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("exercises")
    @Expose
    private ArrayList<ExerciseWelfare> exercises = null;
    @SerializedName("is_finished")
    @Expose
    private Boolean isFinished;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(description);
        out.writeString(date);
        out.writeList(exercises);
        out.writeInt(isFinished ? 1 : 0);
    }

    @SuppressWarnings("unchecked")
    private PlanWelfare(Parcel in) {
        user = in.readString();
        description = in.readString();
        date = in.readString();
        exercises = new ArrayList<ExerciseWelfare>();
        exercises = in.readArrayList(ExerciseWelfare.class.getClassLoader());
        isFinished = (in.readInt() == 0) ? false : true;
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<PlanWelfare> CREATOR =
            new Parcelable.Creator<PlanWelfare>() {
                public PlanWelfare createFromParcel(Parcel in) {
                    return new PlanWelfare(in);
                }

                public PlanWelfare[] newArray(int size) {
                    return new PlanWelfare[size];
                }
            };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ExerciseWelfare> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseWelfare> exercises) {
        this.exercises = exercises;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

}
