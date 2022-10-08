package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.adapters.HomeAdapter;
import com.user.grocerydeliveryapp.adapters.NotificationAdapter;
import com.user.grocerydeliveryapp.databinding.ActivityHomeBinding;
import com.user.grocerydeliveryapp.model.SuccessResGetNotificationCount;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocerydeliveryapp.retrofit.Constant.USER_ID;
import static com.user.grocerydeliveryapp.retrofit.Constant.showToast;

public class HomeAct extends AppCompatActivity {

    ActivityHomeBinding binding;

    public static NavController navController;

    private GroceryDeliveryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_account)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.imgHeader.setOnClickListener(v ->
                {
                    startActivity(new Intent(HomeAct.this, NotificationAct.class));
                }
        );

    }

    private void getProfile() {

        String userId = SharedPreferenceUtility.getInstance(HomeAct.this).getString(USER_ID);

        DataManager.getInstance().showProgressMessage(HomeAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("notification_for","driver");
        Call<SuccessResGetNotificationCount> call = apiInterface.getNotificationCount(map);
        call.enqueue(new Callback<SuccessResGetNotificationCount>() {
            @Override
            public void onResponse(Call<SuccessResGetNotificationCount> call, Response<SuccessResGetNotificationCount> response) {

                DataManager.getInstance().hideProgressMessage();

                try {
                    SuccessResGetNotificationCount data = response.body();

                    if (data.success==1) {

                        String dataResponse = new Gson().toJson(response.body());

                        if(data.notification==0)
                        {
                            binding.tvNotificationCount.setVisibility(View.GONE);
                        } else
                        {
                            binding.tvNotificationCount.setVisibility(View.VISIBLE);
                            binding.tvNotificationCount.setText(data.getNotification()+"");
                        }

                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);

                    } else if (data.success == 0) {
                        showToast(HomeAct.this, data.getMessage());
                        binding.tvNotificationCount.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetNotificationCount> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }
}