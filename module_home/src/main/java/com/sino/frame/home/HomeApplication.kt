package com.sino.frame.home

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.sino.frame.base.app.ApplicationLifecycle

/**
 * Home组件的伪Application
 *
 * @author
 * @since 2021/8/5 11:02 下午
 */
@AutoService(ApplicationLifecycle::class)
class HomeApplication : ApplicationLifecycle {

    override fun onAttachBaseContext(context: Context) {}

    override fun onCreate(application: Application) {}

    override fun onTerminate(application: Application) {}

    override fun initByFrontDesk(): MutableList<() -> String> = mutableListOf()

    override fun initByBackstage() {}
}