package com.maad.bankticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class NearestBranch extends AppCompatActivity {

    private ArrayList<Branch> branches = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private Button branchnamebtn;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_branch);

        branchnamebtn = findViewById(R.id.branchnamebtn);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            getLocation();
        else
            requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            getLocation();
        else {
            Toast.makeText(this, R.string.acceptpermission, Toast.LENGTH_LONG).show();
            branchnamebtn.setVisibility(View.INVISIBLE);
        }
    }

    // Got last known location.
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("trace", "Location: " + location.getLatitude()
                                    + ", " + location.getLongitude());
                            showNearestPlace(location);
                        } else {
                            Toast.makeText(NearestBranch.this, R.string.failedlocation
                                    , Toast.LENGTH_SHORT).show();
                            branchnamebtn.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    //Requesting runtime permission starting from Android Marshmallow
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , 101);
        }
    }

    private void showNearestPlace(Location currentLocation) {
        branches.add(new Branch(29.9602, 31.2569, getString(R.string.maadi)));
        branches.add(new Branch(30.0395, 31.2025, getString(R.string.dokki)));
        branches.add(new Branch(29.8403, 31.2982, getString(R.string.helwan)));


        //Suppose that the first branch will be displayed
        String name = branches.get(0).getName();
        lat = branches.get(0).getLatitude();
        lon = branches.get(0).getLongitude();

        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lon);

        float minimumDistance = location.distanceTo(currentLocation);


        Log.d("trace", "Distance: " + minimumDistance);
        for (int i = 1; i < branches.size(); ++i) {
            location.setLatitude(branches.get(i).getLatitude());
            location.setLongitude(branches.get(i).getLongitude());
            float calculatedDistance = location.distanceTo(currentLocation);
            Log.d("trace", "Distance: " + calculatedDistance);
            if (calculatedDistance < minimumDistance) {
                minimumDistance = calculatedDistance;
                name = branches.get(i).getName();
                lat = branches.get(i).getLatitude();
                lon = branches.get(i).getLongitude();
            }
        }

        Log.d("trace", "Minimum Distance: " + minimumDistance);

        branchnamebtn.setText(name);

    }

    public void goToBranch(View view) {
        //Uri locationUri = Uri.parse("geo:" + lat + ',' + lon);
        Uri locationUri = Uri.parse("google.navigation:q=" + lat + ',' + lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}