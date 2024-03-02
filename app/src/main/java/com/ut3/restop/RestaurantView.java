package com.ut3.restop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RestaurantView extends AppCompatActivity{

    private Restaurant restaurant;
    private Bitmap restaurantImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        RestaurantParcel restaurantParcel = intent.getParcelableExtra("restaurant");
        restaurant = restaurantParcel.getRestaurant();
        ImageParcel parcelableImage = intent.getParcelableExtra("image");
        restaurantImage = parcelableImage.getImage();

        setContentView(R.layout.restau);
        displayRestauInformation();

        List<Menu> menus = getMenuList();

        // Obtenez le conteneur de la liste dans votre layout
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout menuContainer = findViewById(R.id.menus_list);

        // Ajoutez une carte pour chaque restaurant dans la liste
        for (Menu menu : menus) {
            MenuCardView cardView = new MenuCardView(this);
            cardView.setMenuName(menu.getName());
            cardView.setMenuPrice(menu.getPrice());
            cardView.setMenuIngredients(menu.getIngredients());

            menuContainer.addView(cardView);
        }
        Button reservationButton = findViewById(R.id.restau_reserver);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ReservationActivity.class);
                context.startActivity(intent);
            }
        });

    }

    private void displayRestauInformation() {
        TextView restauNom = findViewById(R.id.restau_nom);
        restauNom.setText(restaurant.getName());
        TextView restauPrix = findViewById(R.id.restau_prix);
        restauPrix.setText(restaurant.getPrice());
        ImageView image = findViewById(R.id.image_restau);
        image.setImageBitmap(restaurantImage);

    }

    private List<Menu> getMenuList() {
        List<Menu> menus = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("pipi");
        ingredients.add("caca");
        ingredients.add("prout");

        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 1", "12.5", ingredients));

        return menus;
    }
}
