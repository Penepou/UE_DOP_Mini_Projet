package com.ut3.restop.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ut3.restop.EntityCardView.CommentCardView;
import com.ut3.restop.EntityCardView.MenuCardView;
import com.ut3.restop.Entity.Comment;
import com.ut3.restop.Entity.Menu;
import com.ut3.restop.Entity.Restaurant;
import com.ut3.restop.Parcel.ImageParcel;
import com.ut3.restop.R;
import com.ut3.restop.Parcel.RestaurantParcel;

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
        List<Comment> comments = getCommentList();

        // Obtenez le conteneur de la liste dans votre layout
        LinearLayout menuContainer = findViewById(R.id.menus_list);
        LinearLayout commentContainer = findViewById(R.id.comment_list);

        // Ajoutez une carte pour chaque restaurant dans la liste
        for (Menu menu : menus) {
            MenuCardView cardView = new MenuCardView(this);
            cardView.setMenuName(menu.getName());
            cardView.setMenuPrice(menu.getPrice());
            cardView.setMenuIngredients(menu.getIngredients());

            menuContainer.addView(cardView);
        }

        for (Comment comment : comments) {
            CommentCardView cardView = new CommentCardView(this);
            cardView.setTitleTextView(comment.getTitle());
            cardView.setDescriptionTextView(comment.getDescription());
            cardView.setPseudoTextView(comment.getPseudo());

            commentContainer.addView(cardView);
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

        Button laisserAvisButton = findViewById(R.id.restau_avis);
        laisserAvisButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CommentActivity.class);
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

    private List<Comment> getCommentList() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("trop bien", "c e tait trop bien", "test", "connard"));
        comments.add(new Comment("nul", "c e tait trop bien", "test", "caca"));
        comments.add(new Comment("jsais pas", "c e tait trop bien", "test", "oui"));

        return comments;
    }
}
