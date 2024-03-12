package com.ut3.restop.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ut3.restop.Entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentParcel implements Parcelable {

    private String title;
    private String description;
    private List<String> images;

    public CommentParcel(Comment comment) {
        this.title = comment.getTitle();
        this.description = comment.getDescription();
        this.images = comment.getImages();
    }

    protected CommentParcel(Parcel in) {
        title = in.readString();
        description = in.readString();
        images = new ArrayList<>();
        in.readList(images, String.class.getClassLoader());
    }

    public static final Creator<CommentParcel> CREATOR = new Creator<CommentParcel>() {
        @Override
        public CommentParcel createFromParcel(Parcel in) {
            return new CommentParcel(in);
        }

        @Override
        public CommentParcel[] newArray(int size) {
            return new CommentParcel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeList(images);
    }

    public Comment getComment() {
        return new Comment(title, description, images);
    }
}
