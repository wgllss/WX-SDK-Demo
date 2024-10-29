package com.wx.sdk5

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.clz.scale.sdk.notification.WXNotificationListener
import com.clz.scale.sdk.notification.WXNotificationManager

class MyService : Service() {

    private val notificationManager by lazy { WXNotificationManager(this, BXNotificationListener()) }
    private var isForegroundService = false

    private val binder by lazy { IBinderImpl() }


    override fun onCreate() {
        super.onCreate()
        notificationManager.showNotification()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private inner class IBinderImpl : IMyAidlInterface.Stub() {
        override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
            android.util.Log.e("MyService", "在此包SDK 内可以做相关操作，如打印票据，识别，或者其他的等等")
        }
    }

    private inner class BXNotificationListener : WXNotificationListener {

        override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean) {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(this@MyService, Intent(this@MyService, this@MyService.javaClass))
                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            android.util.Log.e("BackgroundService", "onNotificationCancelled")
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }
}