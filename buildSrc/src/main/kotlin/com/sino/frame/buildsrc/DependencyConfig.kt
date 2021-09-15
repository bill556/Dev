package com.sino.frame.buildsrc

/**
 * 项目依赖版本统一管理
 *
 * @author
 * @since 2021/7/25 4:45 下午
 */
object DependencyConfig {

    /**
     * 依赖版本号
     *
     * @author
     * @since 4/24/21 4:01 PM
     */
    object Version {

        // AndroidX--------------------------------------------------------------
        const val AppCompat = "1.2.0"
        const val CoreKtx = "1.3.1"
        const val ConstraintLayout = "2.0.1"                // 约束布局
        const val TestExtJunit = "1.1.2"
        const val TestEspresso = "3.3.0"
        const val ActivityKtx = "1.1.0"
        const val FragmentKtx = "1.2.5"
        const val MultiDex = "2.0.1"

        // Android---------------------------------------------------------------
        const val Junit = "4.13"
        const val Material = "1.2.1"                        // 材料设计UI套件

        // Kotlin----------------------------------------------------------------
        const val Kotlin = "1.5.21"
        const val Coroutines = "1.5.1"                      // 协程

        // JetPack---------------------------------------------------------------
        const val Lifecycle = "2.3.1"                       // Lifecycle相关（ViewModel & LiveData & Lifecycle）
        const val Hilt = "2.35.1"                           // DI框架-Hilt
        const val HiltAndroidx = "1.0.0"
        const val Room = "2.3.0"                            // 官方推荐数据库框架

        // GitHub----------------------------------------------------------------
        const val OkHttp = "4.9.0"                          // OkHttp
        const val OkHttpInterceptorLogging = "4.9.1"        // OkHttp 请求Log拦截器
        const val Retrofit = "2.9.0"                        // Retrofit
        const val RetrofitConverterGson = "2.9.0"           // Retrofit Gson 转换器
        const val Gson = "2.8.7"                            // Gson
        const val MMKV = "1.2.10"                           // 腾讯 MMKV 替代SP
        const val AutoSize = "1.2.1"                        // 屏幕适配
        const val ARoute = "1.5.1"                          // 阿里路由
        const val ARouteCompiler = "1.5.1"                  // 阿里路由 APT
        const val RecyclerViewAdapter = "3.0.4"             // RecyclerViewAdapter
        const val EventBus = "3.2.0"                        // 事件总线
        const val PermissionX = "1.4.0"                     // 权限申请
        const val LeakCanary = "2.7"                        // 检测内存泄漏
        const val AutoService = "1.0"                       // 自动生成SPI暴露服务文件
        const val Coil = "1.3.0"                            // Kotlin图片加载框架
        const val SmartRefreshLayout = "2.0.3"              //https://github.com/scwang90/SmartRefreshLayout
        const val ShadowLayout = "3.2.1"                    //https://github.com/lihangleo2/ShadowLayout
        const val BackgroundLibrary = "1.7.2"               //https://github.com/JavaNoober/BackgroundLibrary
        const val LoadSir = "1.3.8"                         //https://github.com/KingJA/LoadSir
        const val KvPref = "1.2"                            //https://github.com/EspoirX/KvPref
        const val Logger = "2.2.0"                          //https://github.com/orhanobut/logger
        const val Lottie = "4.1.0"                          //https://github.com/airbnb/lottie-android


        // 第三方SDK--------------------------------------------------------------
        const val TencentBugly = "3.3.9"                    // 腾讯Bugly 异常上报
        const val TencentBuglyNative = "3.8.0"              // Bugly native异常上报
        const val TencentTBSX5 = "44085"                    // 腾讯X5WebView

        // 组件化依赖--------------------------------------------------------------
        const val LibBase = "1.0.1-SNAPSHOT"                // 依赖库base
        const val LibCommon = "1.0.1-SNAPSHOT"              // 依赖库base
    }

    /**
     * AndroidX相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object AndroidX {
        const val AndroidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val AppCompat = "androidx.appcompat:appcompat:${Version.AppCompat}"
        const val CoreKtx = "androidx.core:core-ktx:${Version.CoreKtx}"
        const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:${Version.ConstraintLayout}"
        const val TestExtJunit = "androidx.test.ext:junit:${Version.TestExtJunit}"
        const val TestEspresso = "androidx.test.espresso:espresso-core:${Version.TestEspresso}"
        const val ActivityKtx = "androidx.activity:activity-ktx:${Version.ActivityKtx}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${Version.FragmentKtx}"
        const val MultiDex = "androidx.multidex:multidex:${Version.MultiDex}"
    }

    /**
     * Android相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object Android {
        const val Junit = "junit:junit:${Version.Junit}"
        const val Material = "com.google.android.material:material:${Version.Material}"
    }

    /**
     * JetPack相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object JetPack {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Lifecycle}"
        const val ViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.Lifecycle}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Lifecycle}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Lifecycle}"
        const val LifecycleCompilerAPT = "androidx.lifecycle:lifecycle-compiler:${Version.Lifecycle}"
        const val HiltCore = "com.google.dagger:hilt-android:${Version.Hilt}"
        const val HiltApt = "com.google.dagger:hilt-compiler:${Version.Hilt}"
        const val HiltAndroidx = "androidx.hilt:hilt-compiler:${Version.HiltAndroidx}"
        const val Room = "androidx.room:room-runtime:${Version.Room}"
        const val RoomCompiler = "androidx.room:room-compiler:${Version.Room}"
        const val RoomKtx = "androidx.room:room-ktx:${Version.Room}"
    }

    /**
     * Kotlin相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object Kotlin {
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.Kotlin}"
        const val CoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.Coroutines}"
        const val CoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.Coroutines}"
    }

    /**
     * GitHub及其他相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object GitHub {
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Version.OkHttp}"
        const val OkHttpInterceptorLogging = "com.squareup.okhttp3:logging-interceptor:${Version.OkHttpInterceptorLogging}"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Version.Retrofit}"
        const val RetrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Version.RetrofitConverterGson}"
        const val Gson = "com.google.code.gson:gson:${Version.Gson}"
        const val MMKV = "com.tencent:mmkv-static:${Version.MMKV}"
        const val AutoSize = "me.jessyan:autosize:${Version.AutoSize}"
        const val ARoute = "com.alibaba:arouter-api:${Version.ARoute}"
        const val ARouteCompiler = "com.alibaba:arouter-compiler:${Version.ARouteCompiler}"
        const val RecyclerViewAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Version.RecyclerViewAdapter}"
        const val EventBus = "org.greenrobot:eventbus:${Version.EventBus}"
        const val EventBusAPT = "org.greenrobot:eventbus-annotation-processor:${Version.EventBus}"
        const val PermissionX = "com.permissionx.guolindev:permissionx:${Version.PermissionX}"
        const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:${Version.LeakCanary}"
        const val AutoService = "com.google.auto.service:auto-service:${Version.AutoService}"
        const val AutoServiceAnnotations = "com.google.auto.service:auto-service-annotations:${Version.AutoService}"
        const val Coil = "io.coil-kt:coil:${Version.Coil}"
        const val CoilGIF = "io.coil-kt:coil-gif:${Version.Coil}"
        const val CoilSVG = "io.coil-kt:coil-svg:${Version.Coil}"
        const val CoilVideo = "io.coil-kt:coil-video:${Version.Coil}"
        const val SmartRefreshLayout = "com.scwang.smart:refresh-layout-kernel:${Version.SmartRefreshLayout}"
        const val SmartRefreshLayoutHeader = "com.scwang.smart:refresh-header-classics:${Version.SmartRefreshLayout}"
        const val SmartRefreshLayoutFooter = "com.scwang.smart:refresh-footer-classics:${Version.SmartRefreshLayout}"
        const val ShadowLayout = "com.github.lihangleo2:ShadowLayout:${Version.ShadowLayout}"
        const val BackgroundLibrary = "com.github.JavaNoober.BackgroundLibrary:libraryx:${Version.BackgroundLibrary}"
        const val LoadSir = "com.kingja.loadsir:loadsir:${Version.LoadSir}"
        const val KvPref = "com.github.EspoirX:KvPref:v${Version.KvPref}"
        const val Logger = "com.orhanobut:logger:${Version.Logger}"
        const val Lottie = "com.airbnb.android:lottie:${Version.Lottie}"
    }

    /**
     * SDK相关依赖
     *
     * @author
     * @since 2021/7/25 4:45 下午
     */
    object SDK {
        const val TencentBugly = "com.tencent.bugly:crashreport:${Version.TencentBugly}"
        const val TencentBuglyNative = "com.tencent.bugly:nativecrashreport:${Version.TencentBuglyNative}"
        const val TencentTBSX5 = "com.tencent.tbs:tbssdk:${Version.TencentTBSX5}"
    }

    /**
     * 组件化/模块化相关依赖
     *
     * @author
     * @since 2021年08月16日16:05:40
     */
    object Modular {
        const val LibraryBase = "com.sino.frame:library-base:${Version.LibBase}"
        const val LibraryCommon = "com.sino.frame:library-common:${Version.LibCommon}"
    }
}