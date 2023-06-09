package com.example.locationtask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity<LocationTrack> extends AppCompatActivity {



            private static final int REQUEST_LOCATION = 1;
            Button btnGetLocation;
            TextView showLocation;
            LocationManager locationManager;
            String latitude, longitude;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                showLocation = findViewById(R.id.showLocation);
                btnGetLocation = findViewById(R.id.btnGetLocation);
                btnGetLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            OnGPS();
                        } else {
                            getLocation();
                        }
                    }
                });
            }
            private void OnGPS() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            private void getLocation() {
                if (ActivityCompat.checkSelfPermission(
                        MainActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        MainActivity.this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (locationGPS != null) {
                        double lat = locationGPS.getLatitude();
                        double longi = locationGPS.getLongitude();
                        latitude = String.valueOf(lat);
                        longitude = String.valueOf(longi);
                        showLocation.setText("Your Location: " + " " + "Latitude: " + latitude + " " + "Longitude: " + longitude);
                    } else {
                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
}