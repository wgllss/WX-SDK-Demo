package com.wx.demo

import android.app.Application
import android.content.Context
import com.wx.iml.IXLogLoader
import com.wx.iml.WXLogger

class SampleApplication : Application() {

    companion object {
        lateinit var xLogLoader: IXLogLoader
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        WXLogger.getIXLogLoader(this).also {
            xLogLoader = it
        }.load(this)
    }
}