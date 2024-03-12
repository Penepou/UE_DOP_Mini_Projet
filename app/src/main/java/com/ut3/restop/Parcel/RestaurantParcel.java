package com.ut3.restop.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ut3.restop.Entity.Comment;
import com.ut3.restop.Entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantParcel implements Parcelable {

    private String id;
    private String name;
    private String price;
    private String image;
    private List<CommentParcel> comments;

    public RestaurantParcel(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.price = restaurant.getPrice();
        this.image = restaurant.getImage();
        this.comments = new ArrayList<>();
        for (Comment comment : restaurant.getComments()) {
            this.comments.add(new CommentParcel(comment));
        }
    }

    protected RestaurantParcel(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        image = in.readString();
        comments = new ArrayList<>();
        in.readTypedList(comments, CommentParcel.CREATOR);
    }

    public static final Parcelable.Creator<RestaurantParcel> CREATOR = new Creator<RestaurantParcel>() {
        @Override
        public RestaurantParcel createFromParcel(Parcel in) {
            return new RestaurantParcel(in);
        }

        @Override
        public RestaurantParcel[] newArray(int size) {
            return new RestaurantParcel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public List<CommentParcel> getComments() {
        return comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image);
        dest.writeTypedList(comments);
    }

    public Restaurant getRestaurant() {
        List<Comment> commentList = new ArrayList<>();
        for (CommentParcel commentParcel : comments) {
            commentList.add(commentParcel.getComment());
        }
        return new Restaurant(id, name, price, image, commentList);
    }
}
