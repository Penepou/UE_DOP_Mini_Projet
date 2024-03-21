package com.ut3.restop.Screen;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditImageActivity extends AppCompatActivity  implements SensorEventListener {
    private ImageView imageView;
    private Bitmap imageBitmap;
    private Bitmap imageResult;
    private float brightnessValue = 0;
    private boolean brightness = false;
    private boolean grey = false;
    private ViewGroup stickerContainer; // Le ViewGroup pour contenir les stickers
    private List<Bitmap> stickerBitmaps; // La liste des bitmaps pour les stickers


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        imageView = findViewById(R.id.imageView);
        imageBitmap = getIntent().getParcelableExtra("imageBitmap");
        int indice = getIntent().getIntExtra("indice",0);
        imageResult = imageBitmap;
        imageView.setImageBitmap(imageResult);
        imageView.setFocusable(true);
        imageView.setClickable(true);

        imageView.setOnDragListener( (v, e) -> {

            // Handle each of the expected events.
            switch(e.getAction()) {

                case DragEvent.ACTION_DROP:

                    ClipData.Item item = e.getClipData().getItemAt(0);
                    if (item != null) {
                        // Récupérer la chaîne encodée
                        String bitmapString = item.getText().toString();
                        // Maintenant, vous pouvez utiliser la chaîne pour décoder le Bitmap
                        Bitmap bitmap = stringToBitmap(bitmapString);

                        // Récupérer les coordonnées du drop
                        int x = (int) e.getX();
                        int y = (int) e.getY();

                        // Insérer le sticker dans l'image principale
                        insertStickerIntoImage(bitmap, x, y);

                        // Afficher à nouveau le sticker
                        v.setVisibility(View.VISIBLE);
                    }

                    return true;

                // An unknown action type is received.
                default:
                    return true;
            }
        });

        // Appliquer un filtre à l'image lorsque l'utilisateur clique sur un bouton
        findViewById(R.id.greyFilterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grey){
                    grey = false;
                    imageResult = imageBitmap;
                    imageView.setImageBitmap(imageBitmap);
                }
                else{
                    grey = true;
                    brightness = false;
                    applyGreyFilter();
                }
            }
        });

        findViewById(R.id.brightnessFilterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(brightness){
                    brightness = false;
                    imageResult = imageBitmap;
                    imageView.setImageBitmap(imageBitmap);
                }
                else{
                    brightness = true;
                    grey = false;
                    applyBrightnessFilter(brightnessValue);
                }

            }
        });
        // Supprimer l'image de la page CommentActivity lorsque l'utilisateur clique sur un bouton
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("deleteImage", true);
                resultIntent.putExtra("indice",indice);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Valider l'image
        findViewById(R.id.validateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("image", imageResult);
                resultIntent.putExtra("deleteImage", false);
                resultIntent.putExtra("indice",indice);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Récupérer le ViewGroup pour contenir les stickers
        stickerContainer = findViewById(R.id.sticker_container);

        // Initialiser la liste des bitmaps pour les stickers
        stickerBitmaps = new ArrayList<>();

        // Charger les stickers
        loadStickersFromResource();

        // Ajouter un listener sur le bouton "Ajouter un sticker"
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajouter les stickers au ViewGroup
                addStickersToContainer();
            }
        });

    }

    private void applyGreyFilter() {
        imageResult = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imageResult);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0); // Convertir en niveaux de gris
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(imageBitmap, 0, 0, paint);
        imageView.setImageBitmap(imageResult);
    }

    private void applyBrightnessFilter(float brightnessValue) {
        imageResult = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // Parcourir chaque pixel de l'image d'origine
        for (int x = 0; x < imageBitmap.getWidth(); x++) {
            for (int y = 0; y < imageBitmap.getHeight(); y++) {
                // Obtenir la couleur du pixel
                int pixel = imageBitmap.getPixel(x, y);

                // Extraire les composantes de couleur (rouge, vert, bleu, alpha)
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                // Appliquer le filtre de luminosité
                red = (int) (red * brightnessValue);
                green = (int) (green * brightnessValue);
                blue = (int) (blue * brightnessValue);

                // Limiter les valeurs de couleur entre 0 et 255
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                // Reconstruire la couleur avec les composantes mises à jour
                int filteredPixel = Color.argb(alpha, red, green, blue);

                // Définir le pixel filtré dans l'image de destination
                imageResult.setPixel(x, y, filteredPixel);
            }
        }

        // Afficher l'image filtrée
        imageView.setImageBitmap(imageResult);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            brightnessValue = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    private void loadStickersFromResource() {
        int[] stickerResourceIds = {R.drawable.petoche};
        // Charger les bitmaps pour chaque sticker
        for (int resourceId : stickerResourceIds) {
            Bitmap stickerBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            stickerBitmaps.add(stickerBitmap);
        }
    }

    private void addStickersToContainer() {
        // Ajouter chaque sticker au ViewGroup
        for (Bitmap stickerBitmap : stickerBitmaps) {
            addStickerToContainer(stickerBitmap);
        }
    }

    private void addStickerToContainer(Bitmap stickerBitmap) {
        // Créer un ImageView pour le sticker
        ImageView stickerView = new ImageView(this);
        stickerView.setImageBitmap(stickerBitmap);

        stickerView.setClickable(true);
        stickerView.setFocusable(true);
        stickerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("bitmapString", bitmapToString(stickerBitmap));
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                    return true;
                }
                return  false;
            }
        });

        stickerView.setOnDragListener((v, e) -> {
            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                case DragEvent.ACTION_DRAG_ENTERED:

                case DragEvent.ACTION_DRAG_EXITED:

                case DragEvent.ACTION_DRAG_ENDED:
                    v.invalidate();
                    return true;

                default:
                    break;
            }
            return false;
        });

        // Ajouter le sticker au ViewGroup
        stickerContainer.addView(stickerView);
    }

    private void insertStickerIntoImage(Bitmap sticker, int x, int y) {
        // Obtenir les coordonnées de l'image principale (imageView)
        int[] imageLocation = new int[2];
        imageView.getLocationOnScreen(imageLocation);
        int imageWidth = imageView.getWidth();
        int imageheight = imageView.getHeight();
        // Calculer les coordonnées réelles du drop dans l'image principale
        int imageX = ((x*imageBitmap.getWidth())/ (imageLocation[0] + imageWidth))-15;
        int imageY = ((y*imageBitmap.getHeight())/ (imageLocation[1] + imageheight))-15;
        Bitmap resizedSticker = Bitmap.createScaledBitmap(sticker, 40, 40, false);
        // Ajouter le sticker à l'image principale
        Bitmap initImage = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(initImage);
        canvas.drawBitmap(imageResult, 0, 0, null);
        canvas.drawBitmap(resizedSticker, imageX, imageY, null);
        imageView.setImageBitmap(initImage);
        imageBitmap = initImage;
        imageResult = imageBitmap;
    }

    public String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap stringToBitmap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}