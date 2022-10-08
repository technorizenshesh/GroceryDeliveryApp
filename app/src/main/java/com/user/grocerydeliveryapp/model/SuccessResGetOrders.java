package com.user.grocerydeliveryapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class SuccessResGetOrders implements Serializable {

    @SerializedName("result")
    @Expose
    public List<Result> result = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("success")
    @Expose
    public Integer success;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class DriverDetails {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("date_create")
        @Expose
        public String dateCreate;
        @SerializedName("default_address")
        @Expose
        public String defaultAddress;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("long")
        @Expose
        public String _long;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("country_code")
        @Expose
        public String countryCode;
        @SerializedName("image")
        @Expose
        public String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }

        public String getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(String defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLong() {
            return _long;
        }

        public void setLong(String _long) {
            this._long = _long;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class Result {

        @SerializedName("booking_id")
        @Expose
        public String bookingId;
        @SerializedName("booking_user_id")
        @Expose
        public String bookingUserId;
        @SerializedName("booking_product_id")
        @Expose
        public String bookingProductId;
        @SerializedName("booking_packaging_id")
        @Expose
        public String bookingPackagingId;
        @SerializedName("booking_vouchers_id")
        @Expose
        public String bookingVouchersId;
        @SerializedName("booking_delivery_time")
        @Expose
        public String bookingDeliveryTime;
        @SerializedName("bookig_status")
        @Expose
        public String bookigStatus;
        @SerializedName("driver_id")
        @Expose
        public String driverId;
        @SerializedName("address_id")
        @Expose
        public String addressId;
        @SerializedName("notification_status")
        @Expose
        public String notificationStatus;
        @SerializedName("signature_image")
        @Expose
        public String signatureImage;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("product_image")
        @Expose
        public String productImage;
        @SerializedName("packaging")
        @Expose
        public String packaging;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("gift_vouchers_name")
        @Expose
        public Object giftVouchersName;
        @SerializedName("discount")
        @Expose
        public Object discount;
        @SerializedName("user_address")
        @Expose
        public String userAddress;
        @SerializedName("user_lat")
        @Expose
        public String userLat;
        @SerializedName("user_long")
        @Expose
        public String userLong;
        @SerializedName("company_name")
        @Expose
        public String companyName;
        @SerializedName("company_address")
        @Expose
        public String companyAddress;
        @SerializedName("company_lat")
        @Expose
        public String companyLat;
        @SerializedName("company_long")
        @Expose
        public String companyLong;
        @SerializedName("company_mobile")
        @Expose
        public String companyMobile;
        @SerializedName("driver_details")
        @Expose
        public Object driverDetails;
        @SerializedName("user_details")
        @Expose
        public UserDetails userDetails;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingUserId() {
            return bookingUserId;
        }

        public void setBookingUserId(String bookingUserId) {
            this.bookingUserId = bookingUserId;
        }

        public String getBookingProductId() {
            return bookingProductId;
        }

        public void setBookingProductId(String bookingProductId) {
            this.bookingProductId = bookingProductId;
        }

        public String getBookingPackagingId() {
            return bookingPackagingId;
        }

        public void setBookingPackagingId(String bookingPackagingId) {
            this.bookingPackagingId = bookingPackagingId;
        }

        public String getBookingVouchersId() {
            return bookingVouchersId;
        }

        public void setBookingVouchersId(String bookingVouchersId) {
            this.bookingVouchersId = bookingVouchersId;
        }

        public String getBookingDeliveryTime() {
            return bookingDeliveryTime;
        }

        public void setBookingDeliveryTime(String bookingDeliveryTime) {
            this.bookingDeliveryTime = bookingDeliveryTime;
        }

        public String getBookigStatus() {
            return bookigStatus;
        }

        public void setBookigStatus(String bookigStatus) {
            this.bookigStatus = bookigStatus;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getNotificationStatus() {
            return notificationStatus;
        }

        public void setNotificationStatus(String notificationStatus) {
            this.notificationStatus = notificationStatus;
        }

        public String getSignatureImage() {
            return signatureImage;
        }

        public void setSignatureImage(String signatureImage) {
            this.signatureImage = signatureImage;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getPackaging() {
            return packaging;
        }

        public void setPackaging(String packaging) {
            this.packaging = packaging;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getGiftVouchersName() {
            return giftVouchersName;
        }

        public void setGiftVouchersName(Object giftVouchersName) {
            this.giftVouchersName = giftVouchersName;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserLat() {
            return userLat;
        }

        public void setUserLat(String userLat) {
            this.userLat = userLat;
        }

        public String getUserLong() {
            return userLong;
        }

        public void setUserLong(String userLong) {
            this.userLong = userLong;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getCompanyLat() {
            return companyLat;
        }

        public void setCompanyLat(String companyLat) {
            this.companyLat = companyLat;
        }

        public String getCompanyLong() {
            return companyLong;
        }

        public void setCompanyLong(String companyLong) {
            this.companyLong = companyLong;
        }

        public String getCompanyMobile() {
            return companyMobile;
        }

        public void setCompanyMobile(String companyMobile) {
            this.companyMobile = companyMobile;
        }

        public Object getDriverDetails() {
            return driverDetails;
        }

        public void setDriverDetails(Object driverDetails) {
            this.driverDetails = driverDetails;
        }

        public UserDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

    }
    public class UserDetails {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("date_create")
        @Expose
        public String dateCreate;
        @SerializedName("default_address")
        @Expose
        public String defaultAddress;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("long")
        @Expose
        public String _long;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("country_code")
        @Expose
        public String countryCode;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("register_id")
        @Expose
        public String registerId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }

        public String getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(String defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLong() {
            return _long;
        }

        public void setLong(String _long) {
            this._long = _long;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRegisterId() {
            return registerId;
        }

        public void setRegisterId(String registerId) {
            this.registerId = registerId;
        }

    }


}
