package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.os.Parcel;
import android.os.Parcelable;

import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;

import java.util.ArrayList;

/**
 * Created by Cesar on 11/03/2017.
 */

public class DilemmaPost implements Parcelable{

    private String date;
    private String title;
    private String category;
    private String description;
    private int likeNumber;
    private int commentNumber;
    private ArrayList<CommentItem> comments;

    public DilemmaPost(String date, String title, String category, String description, int likeNumber,
                       int commentNumber, ArrayList<CommentItem> comments){
        this.date = date;
        this.title = title;
        this.category = category;
        this.description = description;
        this.likeNumber = likeNumber;
        this.commentNumber = commentNumber;
        this.comments = comments;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(title);
        out.writeString(category);
        out.writeString(description);
        out.writeInt(likeNumber);
        out.writeInt(commentNumber);
        // out.writeTypedList(questions);
        out.writeList(comments);

    }

    @SuppressWarnings("unchecked")
    private DilemmaPost(Parcel in) {
        date = in.readString();
        title = in.readString();
        category = in.readString();
        description = in.readString();
        likeNumber = in.readInt();
        commentNumber = in.readInt();
        //in.readTypedList(questions, Question.CREATOR);
        comments = new ArrayList<CommentItem>();

        comments = in.readArrayList(CommentItem.class.getClassLoader());
    }

    public int describeContents() {
        return this.hashCode();
    }

    @SuppressWarnings("rawtypes")
    public static final Creator<DilemmaPost> CREATOR =
            new Creator<DilemmaPost>() {
                public DilemmaPost createFromParcel(Parcel in) {
                    return new DilemmaPost(in);
                }

                public DilemmaPost[] newArray(int size) {
                    return new DilemmaPost[size];
                }
            };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
