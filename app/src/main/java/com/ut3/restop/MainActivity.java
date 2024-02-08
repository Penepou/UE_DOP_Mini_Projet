package com.ut3.restop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  
    //private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assumez que vous avez une liste de restaurants
        List<Restaurant> restaurantList = getRestaurantList();

        // Obtenez le conteneur de la liste dans votre layout
        LinearLayout restaurantContainer = findViewById(R.id.restaurants_container);

        // Ajoutez une carte pour chaque restaurant dans la liste
        for (Restaurant restaurant : restaurantList) {
            RestaurantCardView cardView = new RestaurantCardView(this);
            cardView.setRestaurantName(restaurant.getName());
            cardView.setRestaurantPrice(restaurant.getPrice());

            restaurantContainer.addView(cardView);
        }
    }

    private List<Restaurant> getRestaurantList() {
        // Logique pour obtenir la liste des restaurants depuis une source de données
        // (peut être une base de données, un service web, etc.)
        // Retournez une liste fictive pour l'exemple
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


        // ...
        return restaurants;
    }
}
