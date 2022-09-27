package com.user.grocerydeliveryapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivityLoginBinding;
import com.user.grocerydeliveryapp.model.SuccessResLogin;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.Constant;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocerydeliveryapp.retrofit.Constant.isValidEmail;
import static com.user.grocerydeliveryapp.retrofit.Constant.showToast;

public class LoginAct extends AppCompatActivity {

    ActivityLoginBinding binding;

    private String strEmail="";

    private String strPassword="",deviceToken="";

    private GroceryDeliveryInterface apiInterface;

    public static final String TAG = "Login Act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);

      binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

      apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);

      getToken();

      binding.btnLogin.setOnClickListener(v ->
              {
                  validation();
              }
              );
    }

    private void validation() {

        strEmail=binding.etEmail.getText().toString();
        strPassword=binding.etPass.getText().toString();

        if(strEmail.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email.", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(binding.etEmail.getText().toString().trim()))
        {
            Toast.makeText(this, "Please Valid Email.", Toast.LENGTH_SHORT).show();
        }else if(strPassword.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else
        {
//            startActivity(new Intent(SignUpPlumberAct.this,HomePlumberAct.class));
            signup();
        }
    }

    private void signup() {

        DataManager.getInstance().showProgressMessage(LoginAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("email",strEmail);
        map.put("password",strPassword);
        map.put("register_id",deviceToken);

        Call<SuccessResLogin> call = apiInterface.login(map);
        call.enqueue(new Callback<SuccessResLogin>() {
            @Override
            public void onResponse(Call<SuccessResLogin> call, Response<SuccessResLogin> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResLogin data = response.body();

                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        SharedPreferenceUtility.getInstance(LoginAct.this).putString(Constant.USER_ID,data.getUserData().getId());
                        SharedPreferenceUtility.getInstance(getApplication()).putBoolean(Constant.IS_USER_LOGGED_IN, true);
                        startActivity(new Intent(LoginAct.this,HomeAct.class));
                        finish();
                    } else if (data.success == 0) {
                        showToast(LoginAct.this, data.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResLogin> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    private void getToken() {
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            // Get new FCM registration token
                            String token = task.getResult();
                            deviceToken = token;
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(LoginAct.this, "Error=>" + e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}