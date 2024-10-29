package com.clz.scale.sdk.notification

import android.app.Notification

interface WXNotificationListener {

    fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean)

    fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean)

}