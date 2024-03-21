package com.ut3.restop.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.Entity.Restaurant;
import com.ut3.restop.EntityCardView.RestaurantCardView;
import com.ut3.restop.R;
import com.ut3.restop.Service.ImageService;
import com.ut3.restop.Service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    List<Disposable> disposables = new ArrayList<>();
    private ImageService imageService;
    private RestaurantService restaurantService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantService = RestaurantService.getInstance();
        imageService = ImageService.getInstance();
        ImageView imageView = findViewById(R.id.map_icon);
        imageView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, MapActivity.class);
            context.startActivity(intent);
        });
        disposables.add(restaurantService.getRestaurantsList().subscribe(restaurants ->
                displayRestaurants(new ArrayList(restaurants))));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.forEach(disposable -> disposable.dispose());
    }

    private void displayRestaurants(List<Restaurant> restaurantList) {
        LinearLayout restaurantContainer = findViewById(R.id.restaurants_container);
        restaurantContainer.removeAllViews();
        for (Restaurant restaurant : restaurantList) {
            RestaurantCardView cardView = new RestaurantCardView(this);
            //Redirect to restaurantView for each restaurant card view
            cardView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, RestaurantView.class);
                intent.putExtra("restaurantId", restaurant.getId());
                context.startActivity(intent);
            });
            cardView.setRestaurantName(restaurant.getName());
            cardView.setRestaurantPrice(restaurant.getPrice());
            disposables.add(imageService.getImageBitmap(restaurant.getImage()).subscribe(bitmapOpt -> {
                if (bitmapOpt.isPresent()) {
                    cardView.setRestaurantImage(bitmapOpt.get());
                }
            }));
            restaurantContainer.addView(cardView);
        }
    }

}
