package com.example.mbtiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;



/**
 * The most basic example of adding a map to an activity.
 */
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                Toast.makeText(this, "Build.VERSION.SDK_INT >= 23", Toast.LENGTH_LONG);

            } else {
                requestPermission();
            }
        }
        else
        {


        }

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Mapbox.setConnected(true);
                        addMbtiles(mapboxMap);

                    }
                });
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION granted", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this, "PERMISSION is Not Granted", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void addMbtiles(MapboxMap mapboxMap) {
        File mbtilesFile = new File("/sdcard/01-NGO_GIS/mbtiles/resources/raster.mbtiles");
        if (!mbtilesFile.exists()) {
            Toast.makeText(this, "mbtilesFile mot exist", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "mbtilesFile is exist", Toast.LENGTH_LONG).show();
            // I dont no why mbtilesFile.canRead() returns false
            Log.d("MyErr",    "can read=" + mbtilesFile.canRead());
            // can read=false whhy?

            //MBTilesHelper mbTilesHelper = new MBTilesHelper();
            //MBTilesLayer mbTilesLayer = new MBTilesLayer(this, mbtilesFile, mbTilesHelper);
            //mbTilesLayer.addLayerToMap(mapboxMap);

            String filePath = "/sdcard/01-NGO_GIS/mbtiles/resources/raster.mbtiles";
            String sourceId = "ID";


        }

    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "External Storage permission allows us to do read mbtiles. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}