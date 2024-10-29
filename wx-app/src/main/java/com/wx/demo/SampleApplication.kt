package com.wx.demo

import android.app.Application
import android.content.Context
import com.wx.iml.XLogLoaderImpl

class SampleApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        XLogLoaderImpl.instance.load(this)
    }
}