package com.wx.sdk5.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import java.util.concurrent.atomic.AtomicBoolean

class SDK5Impl private constructor() {
    private val isBindService by lazy { AtomicBoolean(false) }
    private var remoteScaleService: IMyAidlInterface? = null

    companion object {
        val instance by lazy { SDK5Impl() }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBindService.set(true)
            try {
                remoteScaleService = IMyAidlInterface.Stub.asInterface(service)
            } catch (e: Exception) {

            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBindService.set(false)
            remoteScaleService = null
        }
    }

    fun bindScaleService(context: Context) {
        try {
            context.packageManager.getApplicationInfo("com.wx.sdk5", 0).enabled
            val intent = Intent("com.wx.sdk5.aidl")
            intent.setPackage("com.wx.sdk5")
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        } catch (e: Exception) {

        }
    }

    fun unBindScaleService(context: Context) {
        remoteScaleService?.takeIf {
            isBindService.get()
        }?.let {
            context.unbindService(connection)
        }
        isBindService.set(false)
    }

    fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String) {
        remoteScaleService?.basicTypes(anInt, aLong, aBoolean, aFloat, aDouble, aString)
    }
}