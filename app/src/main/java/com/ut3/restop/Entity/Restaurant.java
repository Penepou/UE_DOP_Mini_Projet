package com.ut3.restop.Entity;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public String name;
    public String price;

    public String image;
    public List<Comment> comments = new ArrayList<>();

    public String id;

    public Restaurant(){}
    public Restaurant(String id, String name, String price, String image, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.comments = comments;
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
}
