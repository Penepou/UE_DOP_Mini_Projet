package com.ut3.restop.Entity;

public class Comment {
    private String title;
    private String description;
    private String image;
    private String pseudo;

    public Comment(){

    }
    public Comment(String title, String description, String image, String pseudo) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.pseudo = pseudo;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
