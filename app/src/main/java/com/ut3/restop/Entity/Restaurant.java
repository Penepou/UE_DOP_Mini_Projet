package com.ut3.restop.Entity;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public String name;
    public String price;

    public String image;
    public List<Comment> comments = new ArrayList<>();

    public List<Menu> menus = new ArrayList<>();

    public String id;

    public float latitude;

    public float longitude;

    public String address;

    public Restaurant() {
    }

    public Restaurant(String id, String name, String price, String image, List<Comment> comments, List<Menu> menus, float latitude, float longitude, String address) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.comments = comments;
        this.menus = menus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
