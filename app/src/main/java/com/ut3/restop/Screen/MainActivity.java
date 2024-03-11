package com.ut3.restop.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ut3.restop.EntityCardView.RestaurantCardView;
import com.ut3.restop.Entity.Restaurant;
import com.ut3.restop.Parcel.ImageParcel;
import com.ut3.restop.Service.ImageService;
import com.ut3.restop.R;
import com.ut3.restop.Parcel.RestaurantParcel;
import com.ut3.restop.Service.RestaurantService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private ImageService imageService;
    private RestaurantService restaurantService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this, ImageService.class), this, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, RestaurantService.class), this, Context.BIND_AUTO_CREATE);
        ImageView imageView = findViewById(R.id.map_icon);
        imageView.setOnClickListener(v->{
            Context context = v.getContext();
            Intent intent = new Intent(context, MapActivity.class);
            context.startActivity(intent);
        });

    }

    private void fetchAndDisplayRestaurants() {
        LinearLayout restaurantContainer = findViewById(R.id.restaurants_container);
        if (restaurantService != null) {
            restaurantService.getRestaurants().thenAccept(restaurantList -> {
                displayRestaurants(restaurantList, restaurantContainer);
            }).exceptionally(throwable -> {
                return null;
            });
        }
    }

    private void displayRestaurants(List<Restaurant> restaurantList, LinearLayout restaurantContainer){
        restaurantContainer.removeAllViews();
        for (Restaurant restaurant : restaurantList) {
            RestaurantCardView cardView = new RestaurantCardView(this);
            //Redirect to restaurantView for each restaurant card view
            cardView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, RestaurantView.class);
                intent.putExtra("restaurant", new RestaurantParcel(restaurant));
                intent.putExtra("image", new ImageParcel(cardView.getRestaurantImage()));
                context.startActivity(intent);
            });
            cardView.setRestaurantName(restaurant.getName());
            cardView.setRestaurantPrice(restaurant.getPrice());
            imageService.getImageBitmap(restaurant.getImage()).thenAccept(bitmap -> {
                cardView.setRestaurantImage(bitmap);
            });
            restaurantContainer.addView(cardView);
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (componentName.equals(new ComponentName(this, ImageService.class))) {
            ImageService.LocalBinder localBinder = (ImageService.LocalBinder) iBinder;
            imageService = localBinder.getService();
        }
        if (componentName.equals(new ComponentName(this, RestaurantService.class))) {
            RestaurantService.LocalBinder localBinder = (RestaurantService.LocalBinder) iBinder;
            restaurantService = localBinder.getService();
            fetchAndDisplayRestaurants();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        imageService = null;
    }
}
