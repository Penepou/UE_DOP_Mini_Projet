package com.ut3.restop.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ut3.restop.Entity.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RestaurantService extends Service {

        private FirebaseDatabase database;
        private final IBinder binder = new RestaurantService.LocalBinder();

        public class LocalBinder extends Binder {
            public RestaurantService getService() {
                return RestaurantService.this;
            }
        }
        @Override
        public void onCreate() {
            super.onCreate();
            database = FirebaseDatabase.getInstance("https://dopproject-ef7b9-default-rtdb.europe-west1.firebasedatabase.app/");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return binder;
        }


    public CompletableFuture<List<Restaurant>> getRestaurants() {
        CompletableFuture<List<Restaurant>> future = new CompletableFuture<>();
        DatabaseReference databaseRef = database.getReference("restaurants");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Restaurant> restaurantList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    float latitude = snapshot.child("latitude").getValue(float.class);
                    float longitude = snapshot.child("longitude").getValue(float.class);
                    restaurantList.add(new Restaurant(name, price, image, latitude, longitude));
                }
                future.complete(restaurantList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }

}
