package com.ut3.restop.Entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String name;
    private String price;
    private List<String> ingredients = new ArrayList<>();

    private String image;

    public Menu(String name, String price, List<String> ingredients, String image) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.image = image;
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
