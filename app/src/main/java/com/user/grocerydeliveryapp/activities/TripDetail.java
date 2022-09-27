package com.user.grocerydeliveryapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.user.grocerydeliveryapp.R;
import com.user.grocerydeliveryapp.model.SuccessResAcceptOrder;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;
import com.user.grocerydeliveryapp.retrofit.ApiClient;
import com.user.grocerydeliveryapp.retrofit.GroceryDeliveryInterface;
import com.user.grocerydeliveryapp.util.DataManager;
import com.user.grocerydeliveryapp.util.SharedPreferenceUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.user.grocerydeliveryapp.retrofit.Constant.USER_ID;
import static com.user.grocerydeliveryapp.retrofit.Constant.showToast;

public class TripDetail extends AppCompatActivity implements OnMapReadyCallback {

    LinearLayout llBottomSheet;
    private Dialog dialog;
    ImageView ivCancel,ivPhone;
    private GoogleMap mMap;
    ArrayList<LatLng> mMarkerPoints;
    private String pickUpLat = "",pickUpLong = "",dropOffLat = "",dropOffLong = "";
    private SuccessResGetOrders.Result requestModel;
    TextView tvPick,tvDrop;

    private AppCompatButton btnDelivered,btnPicked;

    TextView tvShopAddress,tvProductName,tvProductSize;

    private GroceryDeliveryInterface apiInterface;
    private int selectedPosition = -1;
//    AppCompatButton btnPicked,btnDelivered;
    ImageView ivProduct;
    TextView tvSuperMarket;
    CardView cvBack;
    private String status ="";

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        init();
        allClick();
        Intent in = getIntent();
        if (in!=null)
        {
            result = in.getStringExtra("data");
            requestModel = new Gson().fromJson(result,SuccessResGetOrders.Result.class);
        }

        cvBack.setOnClickListener(v ->
                {
                    finish();
                }
                );

        pickUpLat = requestModel.getCompanyLat();
        pickUpLong = requestModel.getCompanyLong();
        dropOffLat = requestModel.getUserLat();
        dropOffLong = requestModel.getUserLong();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);
        mMarkerPoints = new ArrayList<>();
        tvPick.setText(Html.fromHtml("<u>"+requestModel.getCompanyAddress()+"</u>"));
        tvPick.setOnClickListener(v ->
                {
                    String lat = pickUpLat;
                    String lon = pickUpLong;

//                  String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", lat,lon);
                    String uri = "http://maps.google.com/maps?q=loc:"+lat+","+lon;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    TripDetail.this.startActivity(intent);
                }
                );

        tvDrop.setText(Html.fromHtml("<u>"+requestModel.getUserAddress()+"</u>"));
        tvDrop.setOnClickListener(v ->
                {
                    String lat = dropOffLat;
                    String lon = dropOffLong;
                    //                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", lat,lon);
                    String uri = "http://maps.google.com/maps?q=loc:"+lat+","+lon;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    TripDetail.this.startActivity(intent);
                }
        );

        Glide
                .with(TripDetail.this)
                .load(requestModel.getProductImage())
                .placeholder(R.drawable.male_ic)
                .into(ivProduct);

        status = requestModel.getBookigStatus();

        if(requestModel.getBookigStatus().equalsIgnoreCase("PICKUP"))
        {
            tvSuperMarket.setText(requestModel.getUserDetails().getName());
            tvShopAddress.setText(requestModel.getUserAddress());
            btnDelivered.setVisibility(View.VISIBLE);
            btnPicked.setVisibility(View.GONE);
        }
        else
        {
            tvSuperMarket.setText(requestModel.getCompanyName());
            tvShopAddress.setText(requestModel.getCompanyAddress());
            btnDelivered.setVisibility(View.GONE);
            btnPicked.setVisibility(View.VISIBLE);
        }

        tvProductName.setText(requestModel.getProductName());
        tvProductSize.setText(requestModel.getPackaging());

    }
    private void init()
    {
        ivProduct = findViewById(R.id.iv_product);
        ivPhone = findViewById(R.id.ivCall);
        cvBack= findViewById(R.id.cvBack);
        tvSuperMarket = findViewById(R.id.tvSuperMarket);
        apiInterface = ApiClient.getClient().create(GroceryDeliveryInterface.class);
        tvPick = findViewById(R.id.tvPickLoc);
        tvDrop = findViewById(R.id.tvDropLoc);
        tvShopAddress = findViewById(R.id.tvShopAddress);

        tvProductName = findViewById(R.id.tvShopName);

        tvProductSize = findViewById(R.id.tvQuantity);

        btnDelivered = findViewById(R.id.btnDeliver);

        btnPicked = findViewById(R.id.btnPicked);

        llBottomSheet = findViewById(R.id.botttomSheet);
    }

    public void allClick()
    {

        btnDelivered.setOnClickListener(v ->
                {
                 startActivity(new Intent(TripDetail.this,SignatureAct.class).putExtra("data",new Gson().toJson(requestModel)));
                }
                );

        btnPicked.setOnClickListener(v ->
                {
                    new AlertDialog.Builder(TripDetail.this)
                            .setTitle(R.string.order_picked)
                            .setMessage(R.string.are_you_sure_picked_order)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

//                                    shiftInProgress();
                                    orderPicked(requestModel.getBookingId());

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
        );


//        ivPhone.setOnClickListener(v ->
//                {

////                    Intent callIntent = new Intent(Intent.ACTION_CALL);
////                    callIntent.setData(Uri.parse("tel:"+requestModel.getUserDetails().getMobile()));//change the number
//
//                    if(status.equalsIgnoreCase("Accepted"))
//                    {
//                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "89347854854", null));
//                        startActivity(callIntent);
//                    }
//                    else
//                    {
//                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", requestModel.getUserDetails().getMobile(), null));
//                        startActivity(callIntent);
//                    }
//
//                }
//        );
    }


    public void orderPicked(String strEmail)
    {

        DataManager.getInstance().showProgressMessage(TripDetail.this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("order_id",strEmail);
        map.put("bookig_status","PICKUP");

        Call<SuccessResAcceptOrder> call = apiInterface.orderAccept(map);
        call.enqueue(new Callback<SuccessResAcceptOrder>() {
            @Override
            public void onResponse(Call<SuccessResAcceptOrder> call, Response<SuccessResAcceptOrder> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResAcceptOrder data = response.body();

                    if (data.status.equalsIgnoreCase("1")) {
                        String dataResponse = new Gson().toJson(response.body());
                        Log.e("MapMap", "EDIT PROFILE RESPONSE" + dataResponse);
                        finish();
                    } else if (data.status.equalsIgnoreCase("0")) {
                        showToast(TripDetail.this, data.getMessage());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResAcceptOrder> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        int height = 65;
        int width = 65;
        mMap = googleMap;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dropup);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
        LatLng barcelona = new LatLng(Double.valueOf(pickUpLat),Double.valueOf(pickUpLong));
        mMap.addMarker(new MarkerOptions().position(barcelona).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pick_location)));
        LatLng madrid = new LatLng(Double.valueOf(dropOffLat),Double.valueOf(dropOffLong));
        mMap.addMarker(new MarkerOptions().position(madrid).icon(smallMarkerIcon));
        List<LatLng> path = new ArrayList();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getString(R.string.api_key))
                .build();
        String pick = pickUpLat+","+pickUpLong;
        String drop = dropOffLat+","+dropOffLong;
        DirectionsApiRequest req = DirectionsApi.getDirections(context, pick, drop);
        try {
            DirectionsResult res = req.await();
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];
                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, ""+ex.getLocalizedMessage());
        }
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            mMap.addPolyline(opts);
        }
        zoomMapInitial(new LatLng(Double.parseDouble(pickUpLat),Double.parseDouble(pickUpLong)),new LatLng(Double.parseDouble(dropOffLat),Double.parseDouble(dropOffLong)));
    }

    private void zoomMapInitial(LatLng finalPlace, LatLng currenLoc) {
        try {
            int padding = 200;
            LatLngBounds.Builder bc = new LatLngBounds.Builder();
            bc.include(finalPlace);
            bc.include(currenLoc);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), padding));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}