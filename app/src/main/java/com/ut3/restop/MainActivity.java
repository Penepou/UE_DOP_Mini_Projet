package com.ut3.restop;

import static android.content.ContentValues.TAG;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Restaurant> restaurantList = getRestaurantList();
        LinearLayout restaurantContainer = findViewById(R.id.restaurants_container);

        for (Restaurant restaurant : restaurantList) {
            RestaurantCardView cardView = new RestaurantCardView(this);
            //Redirect to restaurantView for each restaurant card view
            cardView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, RestaurantView.class);
                context.startActivity(intent);
            });
            cardView.setRestaurantName(restaurant.getName());
            cardView.setRestaurantPrice(restaurant.getPrice());

            restaurantContainer.addView(cardView);
        }
    }

    private List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();

        restaurants.add(new Restaurant("Restaurant 1", "10-20"));
        restaurants.add(new Restaurant("Restaurant 2", "20-30"));
        restaurants.add(new Restaurant("Restaurant 3", "0-20"));
        restaurants.add(new Restaurant("Restaurant 4", "20-70"));
        restaurants.add(new Restaurant("Restaurant 5", "0-40"));
        restaurants.add(new Restaurant("Restaurant 6", "20-60"));
        restaurants.add(new Restaurant("Restaurant 7", "50-90"));
        restaurants.add(new Restaurant("Restaurant 8", "20-30"));
        restaurants.add(new Restaurant("Restaurant 9", "20-50"));

        return restaurants;
    }
}
