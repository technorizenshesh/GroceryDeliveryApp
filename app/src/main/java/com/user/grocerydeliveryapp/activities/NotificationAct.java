package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.adapters.NotificationAdapter;
import com.user.grocerydeliveryapp.databinding.ActivityNotificationBinding;
import com.user.grocerydeliveryapp.model.SuccessResGetNotification;
import com.user.grocerydeliveryapp.model.SuccessResGetNotification;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocerydeliveryapp.retrofit.Constant.USER_ID;
import static com.user.grocerydeliveryapp.retrofit.Constant.showToast;

public class NotificationAct extends AppCompatActivity {

    ActivityNotificationBinding binding;

    private GroceryDeliveryInterface apiInterface;

    ArrayList<SuccessResGetNotification.Notification> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_notification);

        apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);
        
        binding.header.imgHeader.setOnClickListener(v -> finish());

        binding.header.tvtitle.setText(getString(R.string.notification));

        getProfile();
        
    }

    private void getProfile() {

        String userId = SharedPreferenceUtility.getInstance(NotificationAct.this).getString(USER_ID);

        Log.d("TAG", "getProfile: "+userId);

        DataManager.getInstance().showProgressMessage(NotificationAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("notification_for","driver");
        Call<SuccessResGetNotification> call = apiInterface.getNotification(map);
        call.enqueue(new Callback<SuccessResGetNotification>() {
            @Override
            public void onResponse(Call<SuccessResGetNotification> call, Response<SuccessResGetNotification> response) {

                DataManager.getInstance().hideProgressMessage();

                try {
                    SuccessResGetNotification data = response.body();

                    if (data.success==1) {

                        String dataResponse = new Gson().toJson(response.body());

                        notificationList.clear();
                        notificationList.addAll(data.getNotification());
                        binding.rvNotification.setHasFixedSize(true);
                        binding.rvNotification.setLayoutManager(new LinearLayoutManager(NotificationAct.this));
                        binding.rvNotification.setAdapter(new NotificationAdapter(NotificationAct.this,notificationList));
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                       
                    } else if (data.success == 0) {
                        showToast(NotificationAct.this, data.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResGetNotification> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }
}