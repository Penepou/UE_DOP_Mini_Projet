package com.ut3.restop.Entity;

import java.util.List;

public class Comment {
    private String title;
    private String description;
    private List<String> images;

    public Comment(){

    }
    public Comment(String title, String description, List<String> images) {
        this.title = title;
        this.description = description;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImage(List<String> images) {
        this.images = images;
    }
}
