package com.ut3.restop.Screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.Entity.Comment;
import com.ut3.restop.Entity.Menu;
import com.ut3.restop.Entity.Restaurant;
import com.ut3.restop.EntityCardView.CommentCardView;
import com.ut3.restop.EntityCardView.MenuCardView;
import com.ut3.restop.R;
import com.ut3.restop.Service.ImageService;
import com.ut3.restop.Service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;


public class RestaurantView extends AppCompatActivity {
    RestaurantService restaurantService;

    ImageService imageService;
    List<Disposable> disposables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.restau);
        String restaurantId = intent.getStringExtra("restaurantId");
        restaurantService = RestaurantService.getInstance();
        imageService = ImageService.getInstance();


        disposables.add(restaurantService.getRestaurant(restaurantId).subscribe(restaurant -> {
            displayComments(restaurant.getComments());
            disposables.add(imageService.getImageBitmap(restaurant.getImage()).subscribe(imageOpt -> {
                        if (imageOpt.isPresent())
                            displayRestauInformation(restaurant, imageOpt.get());
                    }
            ));
        }));

        List<Menu> menus = getMenuList();

        // Obtenez le conteneur de la liste dans votre layout
        LinearLayout menuContainer = findViewById(R.id.menus_list);

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

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);

        return view;
    }

    private void displayComments(List<Comment> comments) {
        LinearLayout commentContainer = findViewById(R.id.comment_list);
        for (Comment comment : comments) {
            CommentCardView cardView = new CommentCardView(this);
            cardView.setTitleTextView(comment.getTitle());
            cardView.setDescriptionTextView(comment.getDescription());
            cardView.setPseudoTextView("Paul");
            if (!comment.getImages().isEmpty()) {
                disposables.add(imageService.getImageBitmap(comment.getImages().get(0)).subscribe(imageOpt -> {
                            if (imageOpt.isPresent()) {
                                cardView.setImageView(imageOpt.get());
                            }
                        }
                ));
            }
            commentContainer.addView(cardView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.forEach(disposable -> disposable.dispose());
    }

    private void displayRestauInformation(Restaurant restaurant, Bitmap restaurantImage) {
        TextView restauNom = findViewById(R.id.restau_nom);
        restauNom.setText(restaurant.getName());
        TextView restauPrix = findViewById(R.id.restau_prix);
        restauPrix.setText(restaurant.getPrice());
        ImageView image = findViewById(R.id.image_restau);
        image.setImageBitmap(restaurantImage);
        RatingBar ratingBar = findViewById(R.id.restau_note_moyenne);
        ratingBar.setRating(calculerMoyenneNotes(restaurant.getComments()));
        Button laisserAvisButton = findViewById(R.id.restau_avis);
        laisserAvisButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("id", restaurant.getId());
            context.startActivity(intent);
        });

    }

    public float calculerMoyenneNotes(List<Comment> comments) {
        float totalNotes = 0;
        int nbComments = comments.size();
        for (Comment comment : comments) {
            totalNotes += comment.getNote();
        }
        if (nbComments > 0) {
            return totalNotes / nbComments;
        } else {
            return 0;
        }
    }


    private List<Menu> getMenuList() {
        List<Menu> menus = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("steak de boeuf");
        ingredients.add("frites");
        ingredients.add("sauce Tartare");

        menus.add(new Menu("Menu 1", "12.5", ingredients));
        menus.add(new Menu("Menu 2", "10.5", ingredients));
        menus.add(new Menu("Menu 3", "16", ingredients));
        menus.add(new Menu("Menu 4", "9", ingredients));
        menus.add(new Menu("Menu 5", "13", ingredients));
        menus.add(new Menu("Menu 6", "12.5", ingredients));

        return menus;
    }
}
