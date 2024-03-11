package com.ut3.restop.Screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.R;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class CommentActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Récupérer la bitmap de l'objet Intent
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ImageView image = findViewById(R.id.image);
                        image.setImageBitmap(imageBitmap);
                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CommentActivity.this, EditImageActivity.class);
                                intent.putExtra("imageBitmap", imageBitmap);
                                startActivityForResult(intent, 123);
                            }
                        });
                    }
                });
        findViewById(R.id.take_photo).setOnClickListener(v -> dispatchTakePictureIntent());
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
                ImageView image = findViewById(R.id.image);
                // Traiter les données récupérées selon vos besoins
                if (deleteImage) {
                    image.setImageBitmap(null);
                } else {
                    Bitmap result = (Bitmap) data.getExtras().getParcelable("image");
                    image.setImageBitmap(result);
                }
            }
        }
    }
}
