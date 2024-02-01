package com.ut3.restop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

//import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
  
    //private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assumez que vous avez une liste de restaurants
        List<Restaurant> restaurantList = getRestaurantList();

        // Obtenez le conteneur de la liste dans votre layout
        LinearLayout restaurantContainer = findViewById(R.id.restaurant_container);

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
        // ...
        return restaurants;
    }
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                System.out.println("bonjour je suis là");
                Log.d(TAG, "Value is: " + value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
}
