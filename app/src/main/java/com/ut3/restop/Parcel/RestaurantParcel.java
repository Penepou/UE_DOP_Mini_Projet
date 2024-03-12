package com.ut3.restop.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ut3.restop.Entity.Restaurant;

public class RestaurantParcel implements Parcelable {

    private String name;
    private String price;
    private String image;
    public float latitude;
    public float longitude;

    public RestaurantParcel(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.price = restaurant.getPrice();
        this.image = restaurant.getImage();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
    }

    protected RestaurantParcel(Parcel in) {
        name = in.readString();
        price = in.readString();
        image = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
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
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    public Restaurant getRestaurant() {
        return new Restaurant(name,price,image,latitude,longitude);
    }
}
