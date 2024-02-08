package com.ut3.restop;

import java.util.List;

public class Menu {
    public String name;
    public String price;
    public String ingredients = "Contenu : ";
    public Menu(String name, String price, List<String> ingredients) {
        this.name = name;
        this.price = price;
        setIngredients(ingredients);
    }
    public void setIngredients(List<String> ingredients) {
        for(String i : ingredients){
            this.ingredients += ", "+ i;
        }
        this.ingredients+=".";
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

    public void setPrix(String price) {
        this.price = price;
    }

    public String getIngredients(){ return ingredients;}

}
