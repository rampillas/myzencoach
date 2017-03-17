
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable{

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("answers")
    @Expose
    private ArrayList<Answer> answers = null;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(description);
        out.writeInt(isCompleted ? 1 : 0);
        out.writeList(answers);
        //out.writeTypedList(answers);
    }

    private Question(Parcel in) {
        description = in.readString();
        isCompleted = (in.readInt() == 0) ? false : true;
        answers = new ArrayList<Answer>();
        answers = in.readArrayList(Answer.class.getClassLoader());
        //in.readTypedList(answers, Answer.CREATOR);
    }

    public int describeContents() {
        return this.hashCode();
    }

    public static final Parcelable.Creator<Question> CREATOR =
            new Parcelable.Creator<Question>() {
                public Question createFromParcel(Parcel in) {
                    return new Question(in);
                }

                public Question[] newArray(int size) {
                    return new Question[size];
                }
            };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
