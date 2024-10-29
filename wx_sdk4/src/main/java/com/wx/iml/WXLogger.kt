package com.wx.iml

import android.content.Context
import com.wx.iml.classloader.WXClassLoader
import com.wx.iml.utils.DynamicManageUtils
import com.wx.iml.utils.DynamicManageUtils.getDlfn

object WXLogger {

    fun getIXLogLoader(context: Context): IXLogLoader {
        //接入部分怎么动态插件化  注意此处，可以在IXLogLoader 内部实现 下载 IXLogLoader的插件实现 ，判断插件版本，可以做到全动态方式，插件版本可以搞个接口，本地包内默认实现，此类里面判断，然后可以做到版本 下载逻辑，IXLogLoader都全动态的，
        val localFile = DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("IXLogLoade_dex", 1000))
        val xLogLoader = if (localFile.exists()) WXClassLoader(localFile.absolutePath, null, context.classLoader).getInterface(IXLogLoader::class.java, "IXLogLoader的插件式实现类全名")
        else XLogLoaderImpl.instance
        return xLogLoader
    }
}