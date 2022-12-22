package com.example.stb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationMap extends AppCompatActivity {
    Spinner spType;
    Button btFind;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);

        //Assigning variables
        spType = findViewById(R.id.sp_type);
        btFind = findViewById(R.id.bt_find);

        //Initialize array of place name
        String[] placeNameList = {"Stationary", "ATM","Gym", "Coffee shops"};

        //Set adapter on spinner
        spType.setAdapter(new ArrayAdapter<>( LocationMap.this ,
                android.R.layout.simple_spinner_dropdown_item,placeNameList));

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        // Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        // check the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //when permission granted --> call method
            getCurrentLocation();
            btFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //get selected position
                    int i = spType.getSelectedItemPosition();
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            if (placeNameList[i].equals("Stationary")){
                                googleMap.clear();
                                LatLng latLng = new LatLng(40.3337757908653, -3.7636245933093067);
                                MarkerOptions options = new MarkerOptions().position(latLng).title("Fotocopias el Callejón");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.330642258023545, -3.766461090007064);
                                options = new MarkerOptions().position(latLng).title("Papelería Outlet");
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.33056463868576, -3.763370481859187);
                                options = new MarkerOptions().position(latLng).title("Folder Papelerías");
                                googleMap.addMarker(options);
                            }else if(placeNameList[i].equals("ATM")){
                                googleMap.clear();
                                LatLng latLng = new LatLng(40.33303521884112, -3.765631410913238);
                                MarkerOptions options = new MarkerOptions().position(latLng).title("Banco Santander");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.33011343605811, -3.766617791311064);
                                options = new MarkerOptions().position(latLng).title("Caja Rural de Jaén");
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.332337010186265, -3.7619958945868843);
                                options = new MarkerOptions().position(latLng).title("Santander ATM");
                                googleMap.addMarker(options);
                            }else if(placeNameList[i].equals("Gym")){
                                googleMap.clear();
                                LatLng latLng = new LatLng(40.33040916093854, -3.7668596363982014);
                                MarkerOptions options = new MarkerOptions().position(latLng).title("Fitness Booxing gym");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.330237404674264, -3.7646387673277144);
                                options = new MarkerOptions().position(latLng).title("Fitness center Manuel Manchado");
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.33291184547771, -3.7633513069965616);
                                options = new MarkerOptions().position(latLng).title("BeOne Fitness & Sport");
                                googleMap.addMarker(options);
                            }else{
                                googleMap.clear();
                                LatLng latLng = new LatLng(40.33247524813525, -3.7673466199317045);
                                MarkerOptions options = new MarkerOptions().position(latLng).title("Cafetería del Sabatini");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.331220531095916, -3.768176625640069);
                                options = new MarkerOptions().position(latLng).title("Cafetería Cornes");
                                googleMap.addMarker(options);

                                latLng = new LatLng(40.3303580520582, -3.766039971831822);
                                options = new MarkerOptions().position(latLng).title("Café central");
                                googleMap.addMarker(options);
                            }
                        }
                    });
                }
            });
        }else{
            //when permission denied --> request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    private void getCurrentLocation() {
        //get current location of the client
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success
                if (location != null){
                    // syn map
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //initialize lat lng
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            //create marker options
                            MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");

                            //zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            //add marker on map
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //when permission granted --> call method
                getCurrentLocation();
            }
        }
    }

}
