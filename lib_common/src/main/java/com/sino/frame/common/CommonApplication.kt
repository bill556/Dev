package com.sino.frame.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.kingja.loadsir.core.LoadSir
import com.lzx.pref.KvPrefModel
import com.lzx.pref.Serializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.sino.frame.base.BaseApplication
import com.sino.frame.base.BuildConfig
import com.sino.frame.base.app.ApplicationLifecycle
import com.sino.frame.base.constant.VersionStatus
import com.sino.frame.base.utils.ProcessUtils
import com.sino.frame.base.utils.network.NetworkStateClient
import com.sino.frame.common.loadsir.EmptyCallback
import com.sino.frame.common.loadsir.ErrorCallback
import com.sino.frame.common.loadsir.LoadingCallback
import com.sino.frame.common.utils.DynamicTimeFormat
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import java.lang.reflect.Type


/**
 * 项目相关的Application
 *
 * @author
 * @since 4/16/21 3:37 PM
 */
@AutoService(ApplicationLifecycle::class)
class CommonApplication : ApplicationLifecycle {

    companion object {
        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mCommonApplication: CommonApplication

        //static代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, _: RefreshLayout? ->
                ClassicsHeader(BaseApplication.context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { _: Context?, _: RefreshLayout? ->
                ClassicsFooter(BaseApplication.context).setDrawableSize(20f)
            }
        }
    }

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    override fun onAttachBaseContext(context: Context) {
        mCommonApplication = this
    }

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    override fun onCreate(application: Application) {}

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */
    override fun onTerminate(application: Application) {}

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(BaseApplication.context)) {
            list.add { initMMKV() }
            list.add { initARouter() }
            list.add { initNetworkStateClient() }
            list.add { initLoadSir() }
        }
        list.add { initTencentBugly() }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
        initX5WebViewCore()
        initLogger();
    }

    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    private fun initNetworkStateClient(): String {
        NetworkStateClient.register()
        return "NetworkStateClient -->> init complete"
    }

    /**
     * 初始化LoadSir
     * @return String
     */
    private fun initLoadSir(): String {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            //.addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
        return "LoadSir -->> init complete"
    }

    /**
     * 腾讯TBS WebView X5 内核初始化
     */
    private fun initX5WebViewCore() {
        // dex2oat优化方案
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        // 允许使用非wifi网络进行下载
        QbSdk.setDownloadWithoutWifi(true)

        // 初始化
        QbSdk.initX5Environment(BaseApplication.context, object : PreInitCallback {

            override fun onCoreInitFinished() {
                Log.d("ApplicationInit", " TBS X5 init finished")
            }

            override fun onViewInitFinished(p0: Boolean) {
                // 初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
                Log.d("ApplicationInit", " TBS X5 init is $p0")
            }
        })
    }

    /**
     * 初始化Logger
     */
    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            //.showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            //.methodCount(0) // (Optional) How many method line to show. Default 2
            //.methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("sino") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        // 初始化日志
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.VERSION_TYPE != VersionStatus.RELEASE
            }
        })
    }

    /**
     * 腾讯 MMKV 初始化
     */
    private fun initMMKV(): String {
        //实例化需实现Serializer
        KvPrefModel.initKvPref(BaseApplication.application, object : Serializer {
            private val gson = Gson()
            override fun serializeToJson(value: Any?): String? {
                return gson.toJson(value)
            }

            override fun deserializeFromJson(json: String?, type: Type): Any? {
                return gson.fromJson(json, type);
            }
        })
        return "MMKV -->> init complete"
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(BaseApplication.application)
        return "ARouter -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        // 第三个参数为SDK调试模式开关
        //CrashReport.initCrashReport(
        //    BaseApplication.context,
        //    BaseApplication.context.getString(R.string.BUGLY_APP_ID),
        //    BuildConfig.VERSION_TYPE != VersionStatus.RELEASE
        //)
        return "Bugly -->> init complete"
    }
}