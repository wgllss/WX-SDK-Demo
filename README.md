

![4c44ad38cb25dffb5f1251fc35f94aa4.jpeg](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/71d3e3cdd21244b2866369419a31fa3c~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgV2dsbHNz:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMzU2NjYxODM1MDgyNTczIn0%3D&rk3s=f64ab15b&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1730769189&x-orig-sign=t5yKarjD0%2FsHSCzS%2BwxgKbaeci0%3D)

> Android SDK 开发, 是一门艺术，只有都掌握了，才能轻松应对各种场景

## 一、前言
1. 本文介绍思路：  
**本文介绍 5 种 SDK 开发方式，和对应使用场景，难度逐级递增，重点介绍第4,5种方式：  
涉及到插件化方式，思维，和跨进程通信AIDL相关知识**  

![whiteboard_exported_image.png](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/d2fab6ee56f7462badc67e89aa811b05~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgV2dsbHNz:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMzU2NjYxODM1MDgyNTczIn0%3D&rk3s=f64ab15b&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1730769189&x-orig-sign=FJNEnxtME64%2BLQ74Usc8XKx0y2Y%3D)

**第4,5种方式示例工程**   
![15a22b1f-e4aa-4556-b75e-a9e326f70387.jpeg](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/3b4a5d80ba2e4ad2ba312a7e0fddd851~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgV2dsbHNz:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMzU2NjYxODM1MDgyNTczIn0%3D&rk3s=e9ecf3d6&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1730263674&x-orig-sign=UtQ6yvZ0PWwEebW3iXErdlpHWkY%3D)

2. SDK常见的例如：地图SDK, 推送SDK,异常监控SDK,人脸识别SDK,支付SDK,游戏SDK,或者网络相关库，图片加载库，上传，下载库，UI相关库如下拉刷新SDK等
3. 这些SDK相关库，或者相关包都是标准的SDK化产品，很大程度上提高了中小型公司开发APP的效率，避免重复造轮子。
4. SDK开发的结果是标准产品，是对外输出的，提供给其他中小型公司或者商家用的，需要注意原则：
> 1、**`尽最大努力做到稳定`**   
> 2、**`尽最大努力不依赖第三方库`**  
> 3、**`尽最大努力保证包的体积最小`**   
> 4、**`尽最大努力保证性能最佳`**  
> 5、**`尽最大努力保证好兼容性`**  
> 6、**`尽最大努力自己实现动态化可更新`**


#####  [示例工程github](https://github.com/wgllss/WX-SDK-Demo)  
#####  [示例工程gitee](https://gitee.com/wgllss888/WX-SDK-Demo)

## 二、Jar包式SDK
1. jar包式开发是最简单的，它的工程是建的model lib工程，**`build.gradle`** 下
```
plugins {
    id 'com.android.library'
}
```
2. 编译出来在 **`/intermediates/aar_main_jar/`** 下面以 **classes.jar** 形式存在，可以重命名
3. 使用方将JAR文件复制到你的Android项目中的 **`libs`** 目录。如果你的项目没有 **`libs`** 目录，你需要创建它。
4.  打开你的 **`build.gradle`** 文件，确保你在 **`dependencies`** 块中添加了对该JAR文件的引用。例如：

```
dependencies {
    // ... 其他依赖 ...
 
    // 引入JAR文件
    implementation files('libs/yourlibrary.jar')
}
```

5.  同步Gradle，以便让它知道你已经添加了新的依赖。
6.  常见的jar包：如日志库，json解析库，网络库，像okhttp等


## 三、SO库式SDK
1. so库编写，需要涉及到 C  和C++, 有些还要交叉编译相关操作，
2. 在Android Studio下自行编写 C/C++相关代码，涉及到jni相关知识，**`Cmake`** 知识，早些时候是 **`android.Mk`** 相关知识。
3. 编译出来的SO在 **`/intermediates/stripped_native_libs/`** 下面  **`arm64-v8a， armeabi-v7a, x86, x86_64`** 都存在
4. 使用方，将so 拷贝到项目 libs目录下，然后配置好jni的lib:  
```
sourceSets {     
    main { 
      jniLibs.srcDirs = ['libs'] 
    }
}
```
6. 常见的SO库：游戏Unity, 音视频相关处理FFmpeg打包出来的库，扫码，直播等相关用C++写好封装的库


## 四、AAR包式SDK
**AAR包开发和jar的开发方式一样的，只是包含了相关资源，和配置manifest,里面除了纯代码，android 相关四大组件 ，UI 的都可以，比如**  
支付SDK,SDK里面自带了界面，有activity  
有些UI相关库 
有些纯sdk但是需要相关配置权限等  
甚至某些还包含so,资源，全部都有的  

这些开发封装好的，可以直接发布到仓库让使用方调用，也可以直接将aar拷贝到项目工程直接使用  
比如：

```

implementation 'com.scwang.smart:refresh-layout-kernel:2.0.0-alpha-1
```
又或者   
```
implementation files(name: 'xxxxAAVV', ext: 'aar')
```
---------------------------------  我是分割线君  ----------------------------------------------

* **`上面 jar, so, 和 aar都是直接接入项目工程，没法动态修改的，很多sdk发布的越久版本迭代的也会有很多，如2.0版本,3.0版本...等等`**  
* 
* **`下面将介绍可以动态修改SDK写法`**

## 五、Dex插件式SDK
1. 此种方式不常见，需要有插件化基础和思维
2. 前面有2篇文章介绍过dex 插件化相关基础：  
**Jar插件化基础:** [  Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483 "https://juejin.cn/post/7425434773026537483")  
**全动态插件化化配置及 全动态化策略模式:** [ 花式高阶：插件化之Dex文件的高阶用法，极少人知道的秘密](https://juejin.cn/post/7428216743166771212 "https://juejin.cn/post/7428216743166771212")
3. 本实例以最简单的，用插件化方式写一个日子打印的SDK:  
   先建一个 **`model lib 工程 wx_sdk4`**:  
   内部代码为一个简单的日志接口 **`IWXLog`**:
   ```
        interface IWXLog {

            fun e(tag: String, message: String)

            fun i(tag: String, message: String)

            fun v(tag: String, message: String)
        }
        
  
4. 再建一个加载日志的接口IX **`LogLoader`**:
  
   ```
    interface IXLogLoader {

        fun isDownloadSuccess(): Boolean

        fun load(context: Context)

        fun getWXLog(): IWXLog

    }
5. **`IXLogLoader`** 本地默认实现：
     ```
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
6.  再建一个实现全动态化策略切换用于在客户端接入时调用：`WXLogger` 
      ```
    object WXLogger {

        fun getIXLogLoader(context: Context): IXLogLoader {
            //接入部分怎么动态插件化  注意此处，可以在IXLogLoader 内部实现 下载 IXLogLoader的插件实现 ，判断插件版本，可以做到全动态方式，插件版本可以搞个接口，本地包内默认实现，此类里面判断，然后可以做到版本 下载逻辑，IXLogLoader都全动态的，
            val localFile = DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("IXLogLoade_dex", 1000))
            val xLogLoader = if (localFile.exists()) WXClassLoader(localFile.absolutePath, null, context.classLoader).getInterface(IXLogLoader::class.java, "IXLogLoader的插件式实现类全名")
            else XLogLoaderImpl.instance
            return xLogLoader
        }
    }
    ```
8.  再建插件工程： **`wx_sdk4_plugin_impl`** 内部实现真的的日志SDK，处理成插件dex文件，具体怎么操作原理，怎么使用，请看我前面写的插件化文章： **[Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483 "https://juejin.cn/post/7425434773026537483")**
       ```
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
9.  接入端调用：
       ```
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
  真正使用的地方：  
```
textView.setOnClickListener {
    SampleApplication.xLogLoader.getWXLog().e("HomeFragment", "AAAAAAAAAAAA")
}
```
    




## 六、Apk安装式SDK
1. 此种方式：以安装的apk形式存在，但是 **`没有启动桌面图标`** ，可以包含资源，so等，或者不包含都可以，此种方式需要设置层前台进程，保持和当前调用应用通信正常

2. 此种方式是跨进程通信方式SDK:常用于智能设备，如：android系统收银机，收银秤，平板，学习机，机器人，车机等设备

3. 比如：一个收银设备打印小票，打印条码，或者做个本地可以培训识别的sdk,最典型的就是谷歌自己的 **`文字转语音（TTS输出)`**
4. 下面简单示例下：  
5. 配置没有启动桌面图标：
```
//此种方式需要 前台进程，需要弹窗权限
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<application
    android:allowBackup="true"
    android:fullBackupContent="@xml/backup_rules"
    android:largeHeap="true"
    android:requestLegacyExternalStorage="true"
    android:resizeableActivity="false"
    android:supportsRtl="true"
    android:usesCleartextTraffic="true">
    <service
        android:name="com.wx.sdk5.MyService"
        android:enabled="true"
        android:exported="true"
        android:foregroundServiceType="mediaPlayback">
        <!--  foregroundServiceType 可以选择适当的类型          -->
        <intent-filter>
            <action android:name="com.wx.sdk5.aidl" />
        </intent-filter>
    </service>
</application>
```
6. 新建个 **`AIDL`** :
```

interface IMyAidlInterface {

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
```  
7. apk内部的 **`MyService`** 实现：
```

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
```
8. 在调用端，再次单独写成个 lib 工程，处理秤jar,让接入端直接使用jar,该工程内部实现：
  ```

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
```

9. 在接入端调用：  
绑定远端服务
    ```
    SDK5Impl.instance.bindScaleService(this)//
    ```
    解绑远端服务：
    ```
    override fun onDestroy() {
        super.onDestroy()
        SDK5Impl.instance.unBindScaleService(this)
    }
    ```
     真正调用：
     ```
    binding.textSdk5.setOnClickListener {
        SDK5Impl.instance.basicTypes(1, 2L, true, 3.0f, 4.4, "AAA")
    }
    ```

## 七、查看SDK4,SDK5结果：

![fe0855e0-7c42-4758-bcdb-d17aa2641020.jpeg](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/4959997d92764e17b64be1f7f253bdac~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgV2dsbHNz:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMzU2NjYxODM1MDgyNTczIn0%3D&rk3s=e9ecf3d6&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1730273136&x-orig-sign=kBFpijiS2%2FW5MWKjVYIGi2rneCU%3D)





## 总结
本位对 5种常见的SDK 开发方式进行了总结：  
最常见的有 jar,so,和aar,这三种是没法动态自我更新的，  
第4中： dex形式SDK,需要掌握好相关插件化开发知识，并且能理解插件化策略思想，才可以做到全动态化   
第5种：跨进程安装一个无桌面启动的apk方式，其实是可以在调用端第一次绑定时候，可以检查sdk是否有新的版本，这样以安装apk的方式里面，可以包含所有系统功能，so,资源，代码，都可以全部解耦出去，需要注意的是，在aidl 跨进程通信时候，传输的数据不能太大，不要超过（1M-8k）的数据（为什么是这么多，需要自行理解binder跨进程通信）


#### 感谢阅读：

#### 欢迎 点赞、收藏、关注

#### 这里你会学到不一样的东西