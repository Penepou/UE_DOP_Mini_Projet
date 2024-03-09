package com.ut3.restop;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantParcel implements Parcelable {

    private String name;
    private String price;
    private String image;

    public RestaurantParcel(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.price = restaurant.getPrice();
        this.image = restaurant.getImage();
    }

    protected RestaurantParcel(Parcel in) {
        name = in.readString();
        price = in.readString();
        image = in.readString();
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

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image);
    }

    public Restaurant getRestaurant() {
        return new Restaurant(name,price,image);
    }
}
