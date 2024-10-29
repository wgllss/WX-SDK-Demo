package com.clz.scale.sdk.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.wx.sdk5.R

class WXNotificationManager(private val context: Context, private val notificationsListener: WXNotificationListener) {
    companion object {
        private const val CHANNEL_ID = "sdk_channel_00001"
        private const val MSG_START_OR_UPDATE_NOTIFICATION = 0
    }

    private val mainHandler by lazy { Handler(Looper.getMainLooper()) { msg: Message -> this.handleMessage(msg) } }
    private val notificationManagerCompat by lazy { NotificationManagerCompat.from(context) }
    private var isNotificationStarted = false

    private fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            MSG_START_OR_UPDATE_NOTIFICATION -> {
                createNotificationChannel()
                val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                    setSmallIcon(R.mipmap.ic_launcher)
                    setOngoing(true)
                    setContentTitle("SDK5")
                    setShowWhen(false)
                    setWhen(System.currentTimeMillis())
                    setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    priority = NotificationCompat.PRIORITY_HIGH
                    setUsesChronometer(false)
                    setOnlyAlertOnce(true)
                }
                val notification = builder.build()
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return false
                }
                notificationManagerCompat.notify(FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK, notification)
                notificationsListener?.onNotificationPosted(FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK, notification, true)
                isNotificationStarted = true
            }

            else -> return false
        }
        return true
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val mChannel = NotificationChannel(CHANNEL_ID, "sdk", NotificationManager.IMPORTANCE_LOW).apply {
                description = "sdk sdk  "
                setShowBadge(false)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            notificationManagerCompat.createNotificationChannel(mChannel)
        }
    }

    fun showNotification() {
        if (!mainHandler.hasMessages(MSG_START_OR_UPDATE_NOTIFICATION)) {
            mainHandler.sendEmptyMessage(MSG_START_OR_UPDATE_NOTIFICATION)
        }
    }

    fun stopNotification(dismissedByUser: Boolean) {
        if (isNotificationStarted) {
            mainHandler.removeMessages(MSG_START_OR_UPDATE_NOTIFICATION)
            notificationManagerCompat.cancel(FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
            notificationsListener?.onNotificationCancelled(FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK, dismissedByUser)
        }
    }
}