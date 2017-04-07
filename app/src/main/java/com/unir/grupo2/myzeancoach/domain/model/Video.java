
package com.unir.grupo2.myzeancoach.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Video implements Parcelable {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("new_attr")
    @Expose
    private Integer newAttr;
    @SerializedName("is_watched")
    @Expose
    private Boolean isWatched;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @SerializedName("survey")
    @Expose
    private List<Test> survey = null;

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(name);
        out.writeString(url);
        out.writeInt(newAttr);
        out.writeInt(isWatched ? 1 : 0);
        out.writeString(date);
        out.writeString(photoUrl);
        out.writeList(survey);
    }

    @SuppressWarnings("unchecked")
    private Video(Parcel in) {
        user = in.readString();
        name = in.readString();
        url = in.readString();
        newAttr = in.readInt();
        isWatched = (in.readInt() == 0) ? false : true;
        date = in.readString();
        photoUrl = in.readString();
        survey = new ArrayList<Test>();
        survey = in.readArrayList(Test.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<Video> CREATOR =
            new Creator<Video>() {
                public Video createFromParcel(Parcel in) {
                    return new Video(in);
                }

                public Video[] newArray(int size) {
                    return new Video[size];
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNewAttr() {
        return newAttr;
    }

    public void setNewAttr(Integer newAttr) {
        this.newAttr = newAttr;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean isWatched) {
        this.isWatched = isWatched;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Test> getSurvey() {
        return survey;
    }

    public void setSurvey(List<Test> survey) {
        this.survey = survey;
    }

}
