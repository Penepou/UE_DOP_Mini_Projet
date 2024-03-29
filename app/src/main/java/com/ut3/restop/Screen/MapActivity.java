package com.ut3.restop.Screen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ut3.restop.Entity.Restaurant;
import com.ut3.restop.R;
import com.ut3.restop.Service.ImageService;
import com.ut3.restop.Service.RestaurantService;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;


public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private final List<Marker> markerList = new ArrayList<>();
    RestaurantService restaurantService;
    ImageService imageService;
    List<Disposable> disposables = new ArrayList<>();
    private MapView map = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantService = RestaurantService.getInstance();
        imageService = ImageService.getInstance();
        Observable<Collection<Restaurant>> restauList = restaurantService.getRestaurantsList();
        setContentView(R.layout.map);
        Button exitButton = findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        map = findViewById(R.id.map);

        disposables.add(restauList.subscribe(restaurants -> {
            restaurants.forEach(restaurant -> {
                String imgURI = restaurant.getImage();
                float lat = restaurant.getLatitude();
                float longi = restaurant.getLongitude();

                disposables.add(imageService.getImageBitmap(imgURI).subscribe(bitmap -> {
                    if (bitmap.isPresent()) {
                        Bitmap bitmap1 = bitmap.get();
                        Marker m = createMarker(map, lat, longi, bitmap1);
                        m.setTitle(restaurant.getName());
                        markerList.add(m);
                    }
                }));
            });
        }));

        markerList.forEach(marker -> {

            putMarker(map, marker);
        });

        //load/initialize the osmdroid configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();

        centerMap(mapController, 43.60431, 1.44354, 14.0);

        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

// Check if the permissions have been granted
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                // If any of the permissions have not been granted, request them
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.forEach(disposable -> disposable.dispose());
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        permissionsToRequest.addAll(Arrays.asList(permissions).subList(0, grantResults.length));
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void centerMap(IMapController controller, double lat, double longi, double zoom) {
        GeoPoint center = new GeoPoint(lat, longi);
        controller.setCenter(center);
        controller.setZoom(zoom);
    }


    private Marker createMarker(MapView map, double lat, double longi, Drawable d) {
        GeoPoint gp = new GeoPoint(lat, longi);
        Marker marker = new Marker(map);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (48.0f * getResources().getDisplayMetrics().density), (int) (48.0f * getResources().getDisplayMetrics().density), true));
        marker.setIcon(dr);
        marker.setPosition(gp);

        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }

    private Marker createMarker(MapView map, double lat, double longi, Bitmap b) {
        GeoPoint gp = new GeoPoint(lat, longi);
        Marker marker = new Marker(map);
        Drawable dr = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(b, (int) (48.0f * getResources().getDisplayMetrics().density), (int) (48.0f * getResources().getDisplayMetrics().density), true));
        marker.setIcon(dr);
        marker.setPosition(gp);

        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }


    private void putMarker(MapView map, Marker marker) {
        map.getOverlays().add(marker);
    }

    private void addMarker(MapView map, double lat, double longi, Drawable d) {
        Marker marker = createMarker(map, lat, longi, d);
        map.getOverlays().add(marker);
    }
}


