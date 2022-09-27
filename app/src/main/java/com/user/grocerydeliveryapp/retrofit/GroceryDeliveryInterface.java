package com.user.grocerydeliveryapp.retrofit;

import com.user.grocerydeliveryapp.model.SuccessResAcceptOrder;
import com.user.grocerydeliveryapp.model.SuccessResGetNotification;
import com.user.grocerydeliveryapp.model.SuccessResGetNotificationCount;
import com.user.grocerydeliveryapp.model.SuccessResGetOrders;
import com.user.grocerydeliveryapp.model.SuccessResLogin;
import com.user.grocerydeliveryapp.model.SuccessResUpdateProfile;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GroceryDeliveryInterface {

    @FormUrlEncoded
    @POST("login_driver")
    Call<SuccessResLogin> login(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_profile_driver")
    Call<SuccessResLogin> getProfile(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_current_order_driver")
    Call<SuccessResGetOrders> getRequest(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("driver_accept_and_Cancel_order")
    Call<SuccessResAcceptOrder> orderAccept(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("get_all_order_driver")
    Call<SuccessResGetOrders> getAllRequest(@FieldMap Map<String, String> paramHashMap);

    @Multipart
    @POST("update_profile_driver")
    Call<SuccessResUpdateProfile> updateProfile (
            @Part("user_id") RequestBody userId,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("number") RequestBody number,
            @Part("country_code") RequestBody countryCode,
            @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("getNotification")
    Call<SuccessResGetNotification> getNotification(@FieldMap Map<String, String> paramHashMap);

    @FormUrlEncoded
    @POST("getnotificationCount")
    Call<SuccessResGetNotificationCount> getNotificationCount(@FieldMap Map<String, String> paramHashMap);

}
