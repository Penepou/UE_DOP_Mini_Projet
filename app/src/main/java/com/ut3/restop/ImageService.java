package com.ut3.restop;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

}
