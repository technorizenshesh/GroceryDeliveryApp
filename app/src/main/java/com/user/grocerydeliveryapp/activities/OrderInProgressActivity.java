package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivityOrderInProgressBinding;

public class OrderInProgressActivity extends AppCompatActivity {

    ActivityOrderInProgressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_order_in_progress);

       binding.imgHeader.setOnClickListener(v -> finish());
       binding.imgCall.setOnClickListener(v ->
               {
                   Intent callIntent = new Intent(Intent.ACTION_DIAL);
                   callIntent.setData(Uri.parse("tel:"+"917223051025"));//change the number
                   OrderInProgressActivity.this.startActivity(callIntent);
               }
               );

       binding.btnAccept.setOnClickListener(v ->
               {
                   showDialog();
               }
               );

    }

    public void showDialog()
    {
        final Dialog dialog = new Dialog(OrderInProgressActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Widget_Material_ListPopupWindow;
        dialog.setContentView(R.layout.activity_order_delivered);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());

        AppCompatButton btnGetSignature = dialog.findViewById(R.id.btnGetSignature);

        btnGetSignature.setOnClickListener(v ->
                {
                    startActivity(new Intent(OrderInProgressActivity.this,SignatureAct.class));
                }
                );

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}