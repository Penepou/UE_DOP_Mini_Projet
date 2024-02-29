package com.ut3.restop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class RestaurantView extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.restau);

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

        Button laisserAvisButton = findViewById(R.id.restau_avis);
        laisserAvisButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, LaisserAvisView.class);
            context.startActivity(intent);
        });

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
