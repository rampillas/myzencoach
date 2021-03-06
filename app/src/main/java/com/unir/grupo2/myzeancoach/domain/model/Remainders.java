package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 16/03/2017.
 */

public class Remainders implements Parcelable {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("questions")
    @Expose
    private ArrayList<Question> questions;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(description);
        out.writeInt(score);
        out.writeInt(isCompleted ? 1 : 0);
        // out.writeTypedList(questions);
        out.writeList(questions);

    }

    @SuppressWarnings("unchecked")
    private Remainders(Parcel in) {
        user = in.readString();
        description = in.readString();
        score = in.readInt();
        isCompleted = (in.readInt() == 0) ? false : true;
        //in.readTypedList(questions, Question.CREATOR);
        questions = new ArrayList<Question>();

        questions = in.readArrayList(Question.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<Remainders> CREATOR =
            new Parcelable.Creator<Remainders>() {
                public Remainders createFromParcel(Parcel in) {
                    return new Remainders(in);
                }

                public Remainders[] newArray(int size) {
                    return new Remainders[size];
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

}
