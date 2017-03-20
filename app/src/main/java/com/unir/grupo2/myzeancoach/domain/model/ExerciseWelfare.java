
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExerciseWelfare implements Parcelable{

    @SerializedName("week")
    @Expose
    private Integer week;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("audio_url")
    @Expose
    private String audioUrl;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("appreciation")
    @Expose
    private String appreciation;
    @SerializedName("question_exercises")
    @Expose
    private ArrayList<QuestionExerciseWelfare> questionExercises = null;

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(week);
        out.writeString(description);
        out.writeString(audioUrl);
        out.writeString(instructions);
        out.writeString(feedback);
        out.writeString(appreciation);
        out.writeList(questionExercises);
    }

    @SuppressWarnings("unchecked")
    private ExerciseWelfare(Parcel in) {
        week = in.readInt();
        description = in.readString();
        audioUrl = in.readString();
        instructions = in.readString();
        feedback = in.readString();
        appreciation = in.readString();
        questionExercises = new ArrayList<QuestionExerciseWelfare>();
        questionExercises = in.readArrayList(QuestionExerciseWelfare.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator<ExerciseWelfare> CREATOR =
            new Parcelable.Creator<ExerciseWelfare>() {
                public ExerciseWelfare createFromParcel(Parcel in) {
                    return new ExerciseWelfare(in);
                }

                public ExerciseWelfare[] newArray(int size) {
                    return new ExerciseWelfare[size];
                }
            };

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public ArrayList<QuestionExerciseWelfare> getQuestionExercises() {
        return questionExercises;
    }

    public void setQuestionExercises(ArrayList<QuestionExerciseWelfare> questionExercises) {
        this.questionExercises = questionExercises;
    }

}
