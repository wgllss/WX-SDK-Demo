package com.wx.iml

class WXLog : IWXLog {
    override fun e(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }

    override fun i(tag: String, message: String) {
        android.util.Log.i(tag, message)
    }

    override fun v(tag: String, message: String) {
        android.util.Log.v(tag, message)
    }
}