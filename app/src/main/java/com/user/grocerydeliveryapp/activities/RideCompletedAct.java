package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivityRideCompletedBinding;

public class RideCompletedAct extends AppCompatActivity {

    ActivityRideCompletedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this,R.layout.activity_ride_completed);

      binding.imgHeader.setOnClickListener(v-> finish());

    }
}