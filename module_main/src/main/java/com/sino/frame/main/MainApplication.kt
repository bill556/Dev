package com.sino.frame.main

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.sino.frame.base.app.ApplicationLifecycle

/**
 * Main模块的伪Application
 *
 * @author
 * @since 2021/7/28 9:32 下午
 */
@AutoService(ApplicationLifecycle::class)
class MainApplication : ApplicationLifecycle {
    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    override fun onAttachBaseContext(context: Context) {}

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
    override fun initByFrontDesk(): MutableList<() -> String> = mutableListOf()

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {}
}