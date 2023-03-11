package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivityOrderBinding;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;
import com.user.grocerydeliveryapp.model.SuccessResAcceptOrder;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.Constant;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocerydeliveryapp.retrofit.Constant.showToast;

public class OrderAct extends AppCompatActivity {

    ActivityOrderBinding binding;
    private SuccessResGetOrders.Result requestModel;
    private GroceryDeliveryInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_order);
       apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);
       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(R.string.orders_details);
       Intent in = getIntent();
       if (in!=null)
        {
            String result = in.getStringExtra("data");
            requestModel = new Gson().fromJson(result,SuccessResGetOrders.Result.class);
        }

       binding.btnAccept.setOnClickListener(v ->
               {
                   new AlertDialog.Builder(OrderAct.this)
                           .setTitle(getString(R.string.accept))
                           .setMessage(getString(R.string.wanna_accept))
                           // Specifying a listener allows you to take an action before dismissing the dialog.
                           // The dialog is automatically dismissed when a dialog button is clicked.
                           .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   // Continue with delete operation
                                   acceptOrder(requestModel.getBookingId());
                               }
                           })
                           // A null listener allows the button to dismiss the dialog and take no further action.
                           .setNegativeButton(android.R.string.no, null)
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .show();
               }
               );

        binding.tvOrderId.setText("Order ID- "+requestModel.getBookingId());
        binding.tvMyOrder.setText("Order ID- "+requestModel.getBookingId());
        binding.tvFromLocation.setText(requestModel.getCompanyAddress());
        binding.tvtoLocation.setText(requestModel.getUserAddress());
        binding.tvSuperMarket.setText(requestModel.getCompanyName());
        binding.tvShopAddress.setText(requestModel.getCompanyAddress());
        binding.tvShopName.setText(requestModel.getProductName());
        binding.tvQuantity.setText(requestModel.getPackaging());
        Glide.with(this)
                .load(requestModel.getProductImage())
                .into(binding.ivProduct);
        binding.tvCustomerName.setText(requestModel.getUserDetails().getName());
        binding.tvCustomerAddress.setText(requestModel.getUserAddress());
        binding.tvAmount.setText(requestModel.getPrice());
    }

    public void acceptOrder(String strEmail)
    {
        DataManager.getInstance().showProgressMessage(OrderAct.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("order_id",strEmail);
        map.put("bookig_status","PROCESSING");
        Call<ResponseBody> call = apiInterface.orderAccept(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
//                    SuccessResAddComment data = response.body();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String data = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (data.equals("1")) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        finish();

                    } else if (data.equals("0")) {
                        showToast(OrderAct.this,message);
                    }
                } catch (Exception e) {
                    finish();
                    Log.d("TAG", "onResponse: "+e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                finish();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

}