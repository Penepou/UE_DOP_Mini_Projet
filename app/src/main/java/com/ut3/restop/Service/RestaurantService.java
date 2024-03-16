package com.ut3.restop.Service;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ut3.restop.Entity.Comment;
import com.ut3.restop.Entity.Restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class RestaurantService {

    private static RestaurantService instance = null;
    private final FirebaseDatabase database;
    Subject<Map<String, Restaurant>> restaurantsMap = ReplaySubject.createWithSize(1);

    private RestaurantService() {
        database = FirebaseDatabase.getInstance("https://dopproject-ef7b9-default-rtdb.europe-west1.firebasedatabase.app/");
        restaurantsMap.onNext(new HashMap<>());
        getRestaurants();
    }

    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }

    public void getRestaurants() {
        DatabaseReference databaseRef = database.getReference("restaurants");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Restaurant> restaurantList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    String name = snapshot.child("name").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    float latitude = snapshot.child("latitude").getValue(float.class);
                    float longitude = snapshot.child("longitude").getValue(float.class);
                    List<Comment> comments = new ArrayList<>();
                    for (DataSnapshot comment : snapshot.child("comments").getChildren()) {
                        String title = comment.child("title").getValue(String.class);
                        String description = comment.child("description").getValue(String.class);
                        float note = comment.child("note").getValue(float.class);
                        List<String> images = new ArrayList<>();
                        for (DataSnapshot imageComment : comment.child("images").getChildren()) {
                            images.add(imageComment.getValue(String.class));
                        }
                        comments.add(new Comment(title, description, images, note));
                    }
                    restaurantList.add(new Restaurant(id, name, price, image, comments, latitude, longitude));
                }
                Map<String, Restaurant> map = new HashMap<>();
                restaurantList.forEach(resto ->
                        map.put(resto.getId(), resto));
                restaurantsMap.onNext(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Observable<Collection<Restaurant>> getRestaurantsList() {
        return restaurantsMap.map(map -> map.values());
    }

    public Observable<Restaurant> getRestaurant(String id) {
        return restaurantsMap.map(map -> map.get(id));
    }


    public void addCommentToRestaurant(String restaurantId, Comment comment) {
        DatabaseReference restaurantRef = database.getReference("restaurants").child(restaurantId).child("comments").push();
        restaurantRef.setValue(comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


}
