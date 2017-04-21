
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionExerciseWelfare implements Parcelable {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private Boolean answer;
    @SerializedName("is_answered")
    @Expose
    private Boolean isAnswered;
    @SerializedName("response")
    @Expose
    private String response;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(question);
        out.writeInt(answer ? 1 : 0);
        out.writeInt(isAnswered ? 1 : 0);
        out.writeString(response);
    }

    @SuppressWarnings("unchecked")
    private QuestionExerciseWelfare(Parcel in) {
        question = in.readString();
        answer = (in.readInt() == 0) ? false : true;
        isAnswered = (in.readInt() == 0) ? false : true;
        response = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<QuestionExerciseWelfare> CREATOR =
            new Parcelable.Creator<QuestionExerciseWelfare>() {
                public QuestionExerciseWelfare createFromParcel(Parcel in) {
                    return new QuestionExerciseWelfare(in);
                }

                public QuestionExerciseWelfare[] newArray(int size) {
                    return new QuestionExerciseWelfare[size];
                }
            };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public Boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
