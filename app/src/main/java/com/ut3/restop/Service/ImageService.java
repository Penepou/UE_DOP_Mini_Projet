package com.ut3.restop.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class ImageService {

    private static ImageService instance = null;
    private final FirebaseStorage storage;
    Subject<Map<String, Bitmap>> imagesMap = ReplaySubject.createWithSize(1);


    private ImageService() {
        storage = FirebaseStorage.getInstance("gs://dopproject-ef7b9.appspot.com");
        imagesMap.onNext(new HashMap<>());
    }

    public static ImageService getInstance() {
        if (instance == null) {
            instance = new ImageService();
        }
        return instance;
    }

    public Observable<Optional<Bitmap>> getImageBitmap(String Uri) {
        imagesMap.take(1).subscribe(map -> {
            if (!map.containsKey(Uri)) {
                StorageReference imageRef = storage.getReference(Uri);
                imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        map.put(Uri, bitmap);
                        imagesMap.onNext(map);
                    }
                });
            }
        });
        return imagesMap.map(images -> Optional.ofNullable(images.get(Uri)));
    }

    public CompletableFuture<List<String>> saveCommentImages(List<Bitmap> bitmaps) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();

        StorageReference imagesRef = storage.getReference().child("Images/comments");
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
                uris.add("Images/comments" + imageName);
                imageRef.putBytes(data);
            }
        });

        return future;
    }


}
