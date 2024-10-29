package com.wx.iml

interface IWXLog {

    fun e(tag: String, message: String)

    fun i(tag: String, message: String)

    fun v(tag: String, message: String)
}