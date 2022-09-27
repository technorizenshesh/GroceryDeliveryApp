package com.user.grocerydeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivityOrderAccepted2Binding;
import com.user.grocerydeliveryapp.databinding.ActivityOrderAcceptedBinding;

public class OrderAcceptedAct extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ActivityOrderAccepted2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_order_accepted2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.btnAccept.setOnClickListener(v ->
                {


//                    startActivity(new Intent(OrderAcceptedAct.this,OrderInProgressActivity.class));
                }
                );

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng TutorialsPoint = new LatLng(21, 57);
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("GroceryShop"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));

    }
}