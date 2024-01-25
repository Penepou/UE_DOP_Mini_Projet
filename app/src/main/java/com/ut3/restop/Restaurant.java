package com.ut3.restop;

public class Restaurant {
    public String name;
    public String price;

    public Restaurant(String name, String price) {
        this.name = name;
        this.price = price;
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
}
