package com.user.grocerydeliveryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.databinding.ActivitySignatureBinding;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;
import com.user.grocerydeliveryapp.util.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SignatureAct extends AppCompatActivity {

    ActivitySignatureBinding binding;
    private SuccessResGetOrders.Result requestModel;
    private String result;

    private String bitmapString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_signature);
       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.customer_signature));

        Intent in = getIntent();
        if (in!=null)
        {
            result = in.getStringExtra("data");
            requestModel = new Gson().fromJson(result, SuccessResGetOrders.Result.class);
        }

        binding.btnLogin.setOnClickListener(v ->
                {
                    Bitmap bitmap = binding.signaturePad.getSignatureBitmap();
                    Uri tempUri = getImageUri(SignatureAct.this, bitmap);
                    String image = RealPathUtil.getRealPath(SignatureAct.this, tempUri);
                    bitmapString = image;
                    Log.d("TAG", "Bitmap Image Reader : "+ bitmapString);
                }
                );

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title_" + System.currentTimeMillis(), null);
        return Uri.parse(path);
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }

}