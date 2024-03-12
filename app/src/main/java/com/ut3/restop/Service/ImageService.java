package com.ut3.restop.Service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ImageService extends Service {

        private FirebaseStorage storage;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public ImageService getService() {
            return ImageService.this;
        }
    }
        @Override
        public void onCreate() {
            super.onCreate();
            storage = FirebaseStorage.getInstance("gs://dopproject-ef7b9.appspot.com");
        }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public CompletableFuture<Bitmap> getImageBitmap(String Uri){
        CompletableFuture<Bitmap> future = new CompletableFuture<>();
        StorageReference imageRef = storage.getReference(Uri);
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                future.complete(bitmap);
            }
        });
        return future;
    }

    public CompletableFuture<List<String>> saveCommentImages(List<Bitmap> bitmaps) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        StorageReference imagesRef = storage.getReference().child("images/comment");
        imagesRef.listAll().addOnSuccessListener(listResult -> {
            int count = listResult.getItems().size();

            List<String> uris = new ArrayList<>();

            for (int i = 0; i < bitmaps.size(); i++) {
                Bitmap bitmap = bitmaps.get(i);
                String imageName = "image_" + (count + i);

                StorageReference imageRef = imagesRef.child(imageName);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                imageRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
                    // Récupérer l'URI de l'image enregistrée
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        uris.add(uri.toString());
                        // Si toutes les images ont été traitées, compléter le CompletableFuture avec la liste d'URI
                        if (uris.size() == bitmaps.size()) {
                            future.complete(uris);
                        }
                    });
                }).addOnFailureListener(e -> {
                    // En cas d'échec, compléter le CompletableFuture avec une liste vide
                    future.complete(new ArrayList<>());
                });
            }
        });

        return future;
    }


}
