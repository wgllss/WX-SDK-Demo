package com.wx.iml

import android.content.Context

interface IXLogLoader {

    fun isDownloadSuccess(): Boolean

    fun load(context: Context)

    fun getWXLog(): IWXLog

}