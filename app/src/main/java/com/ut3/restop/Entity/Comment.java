package com.ut3.restop.Entity;

import java.util.List;

public class Comment {
    private String title;
    private String description;
    private List<String> images;
    private float note;

    public Comment() {

    }

    public Comment(String title, String description, List<String> images, float note) {
        this.title = title;
        this.description = description;
        this.images = images;
        this.note = note;
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

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setImage(List<String> images) {
        this.images = images;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}
