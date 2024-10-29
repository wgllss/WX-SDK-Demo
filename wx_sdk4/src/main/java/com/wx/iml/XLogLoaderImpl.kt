package com.wx.iml

import android.content.Context
import com.wx.iml.classloader.WXClassLoader
import com.wx.iml.utils.DynamicManageUtils
import com.wx.iml.utils.DynamicManageUtils.getDlfn
import java.util.concurrent.atomic.AtomicBoolean


class XLogLoaderImpl private constructor() : IXLogLoader {

    private var atomicBoolean = AtomicBoolean(false)
    lateinit var context: Context

    companion object {

        val instance by lazy { XLogLoaderImpl() }

    }

    override fun isDownloadSuccess(): Boolean {
        return atomicBoolean.get()
    }

    override fun load(context: Context) {
        this.context = context

        //此处 模拟下载，怎么判断版本，怎么在启动优化中最快读取配置，判断要加载那个版本插件，是否还要下载插件等 前面文章有介绍过
        DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("xlog_dex", 1000)).takeUnless { it.exists() }?.run {
            val isSuccess = DynamicManageUtils.copyFileFromAssetsToSD(context, this, "xlog_dex")
            atomicBoolean.set(isSuccess)
        }
    }

    override fun getWXLog(): IWXLog {
        if (!atomicBoolean.get()) {
            // 如果没有下载成功或者 异常，可以给出相关提示异常
        }
        return WXClassLoader(DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("xlog_dex", 1000)).absolutePath, null, context.classLoader).getInterface(IWXLog::class.java, "com.wx.iml.WXLog")
    }
}