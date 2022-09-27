package com.user.grocerydeliveryapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ravindra Birla on 13,September,2022
 */

public class SuccessResGetNotification implements Serializable {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("notification")
    @Expose
    public List<Notification> notification = null;
    @SerializedName("success")
    @Expose
    public Integer success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public class Notification implements Serializable {

        @SerializedName("notification_id")
        @Expose
        public String notificationId;
        @SerializedName("notification_user_id")
        @Expose
        public String notificationUserId;
        @SerializedName("notification_message")
        @Expose
        public String notificationMessage;
        @SerializedName("notification_type")
        @Expose
        public String notificationType;
        @SerializedName("notification_status")
        @Expose
        public String notificationStatus;
        @SerializedName("notification_for")
        @Expose
        public String notificationFor;
        @SerializedName("notification_date_time")
        @Expose
        public String notificationDateTime;

        public String getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(String notificationId) {
            this.notificationId = notificationId;
        }

        public String getNotificationUserId() {
            return notificationUserId;
        }

        public void setNotificationUserId(String notificationUserId) {
            this.notificationUserId = notificationUserId;
        }

        public String getNotificationMessage() {
            return notificationMessage;
        }

        public void setNotificationMessage(String notificationMessage) {
            this.notificationMessage = notificationMessage;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getNotificationStatus() {
            return notificationStatus;
        }

        public void setNotificationStatus(String notificationStatus) {
            this.notificationStatus = notificationStatus;
        }

        public String getNotificationFor() {
            return notificationFor;
        }

        public void setNotificationFor(String notificationFor) {
            this.notificationFor = notificationFor;
        }

        public String getNotificationDateTime() {
            return notificationDateTime;
        }

        public void setNotificationDateTime(String notificationDateTime) {
            this.notificationDateTime = notificationDateTime;
        }

    }

}