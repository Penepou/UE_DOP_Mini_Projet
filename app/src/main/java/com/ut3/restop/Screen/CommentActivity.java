package com.ut3.restop.Screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.Entity.Comment;
import com.ut3.restop.R;
import com.ut3.restop.Service.ImageService;
import com.ut3.restop.Service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CommentActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private final List<Bitmap> images = new ArrayList<>();
    LinearLayout imagesLayout;
    private ImageService imageService;
    private RestaurantService restaurantService;
    private ActivityResultLauncher<Intent> takePictureLauncher;
    private EditText titleInput;
    private EditText editText;
    private RatingBar ratingBar;
    private Button laisserAvisButton;
    private float commentRating = -1;
    // Écouteur de texte pour les deux EditText
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            updateButtonState();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        restaurantService = RestaurantService.getInstance();
        imageService = ImageService.getInstance();
        imagesLayout = findViewById(R.id.imagesLayout);
        ImageView imageView = findViewById(R.id.map_icon);
        imageView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, MapActivity.class);
            context.startActivity(intent);
        });
        Intent intent = getIntent();
        String idRestaurant = intent.getStringExtra("id");

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Récupérer la bitmap de l'objet Intent
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ImageView image = new ImageView(CommentActivity.this);
                        image.setImageBitmap(imageBitmap);
                        images.add(imageBitmap);
                        int indice = images.size() - 1;
                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CommentActivity.this, EditImageActivity.class);
                                intent.putExtra("imageBitmap", imageBitmap);
                                intent.putExtra("indice", indice);
                                startActivityForResult(intent, 123);
                            }
                        });
                        imagesLayout.addView(image);
                    }
                });
        findViewById(R.id.take_photo).setOnClickListener(v -> dispatchTakePictureIntent());
        titleInput = findViewById(R.id.titleInput);
        editText = findViewById(R.id.descriptionText);
        ratingBar = findViewById(R.id.note);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                commentRating = rating;
                updateButtonState();
            }
        });
        laisserAvisButton = findViewById(R.id.laisserAvis);

        // Désactiver le bouton "laisserAvis" au début
        laisserAvisButton.setEnabled(false);

        // Ajouter des écouteurs de texte pour les deux EditText
        titleInput.addTextChangedListener(textWatcher);
        editText.addTextChangedListener(textWatcher);

        // Ajouter un écouteur de clic pour le bouton "laisserAvis"
        laisserAvisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.isEmpty()) {
                    Comment comment = new Comment(titleInput.getText().toString(), editText.getText().toString(), new ArrayList<>(), commentRating);
                    restaurantService.addCommentToRestaurant(idRestaurant, comment);
                    finish();
                } else {
                    CompletableFuture<List<String>> futureUris = imageService.saveCommentImages(images);
                    futureUris.thenAccept(uris -> {
                        Comment comment = new Comment(titleInput.getText().toString(), editText.getText().toString(), uris, commentRating);
                        restaurantService.addCommentToRestaurant(idRestaurant, comment);
                        finish();
                    }).exceptionally(e -> {
                        return null;
                    });
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                // Récupérer les données de l'activité EditImageActivity
                boolean deleteImage = data.getBooleanExtra("deleteImage", false);
                int indice = data.getIntExtra("indice", 0);
                // Traiter les données récupérées selon vos besoins
                if (deleteImage) {
                    imagesLayout.removeViewAt(indice);
                    images.remove(indice);
                } else {
                    Bitmap result = data.getExtras().getParcelable("image");
                    ImageView image = (ImageView) imagesLayout.getChildAt(indice);
                    image.setImageBitmap(result);
                    images.set(indice, result);
                }
            }
        }
    }

    // Mettre à jour l'état du bouton en fonction du contenu des EditText
    private void updateButtonState() {
        boolean isFieldsFilled = titleInput.getText().length() > 0 && editText.getText().length() > 0 && imageService != null && restaurantService != null && commentRating != -1;
        laisserAvisButton.setEnabled(isFieldsFilled);
    }
}
