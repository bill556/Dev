package com.sino.frame.main.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.sino.frame.base.mvvm.vm.EmptyViewModel
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.ui.BaseActivity
import com.sino.frame.main.databinding.MainActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.jessyan.autosize.internal.CancelAdapt
import android.animation.Animator

import android.animation.AnimatorListenerAdapter


/**
 * 启动页
 *
 * @author
 * @since 2021/7/28 9:47 下午
 */
@AndroidEntryPoint
class SplashActivity : BaseActivity<MainActivitySplashBinding, EmptyViewModel>(), CancelAdapt {

    override val mViewModel: EmptyViewModel by viewModels()

    /**
     * 初始化View
     */
    override fun MainActivitySplashBinding.initView() {
        lavContainer.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                lavContainer.removeAnimatorListener(this)
                //HomeActivity.start(getContext()); //Intent跳转
                ARouter.getInstance()
                    .build(RouteUrl.Main.MainActivity)
                    .navigation()
                finish()
            }
        })

//        lifecycleScope.launch(Dispatchers.Default) {
//            delay(1500)
//            ARouter.getInstance()
//                .build(RouteUrl.Main.MainActivity)
//                .navigation()
//            delay(200)
//            finish()
//        }

    }

    override fun MainActivitySplashBinding.initEvent() {
    }

    /**
     * 订阅LiveData
     */
    override fun initObserve() {}

    /**
     * 用于在页面创建时进行请求接口
     */
    override fun initRequestData() {}

    override fun setStatusBar() {}

    override fun onRetryBtnClick() {
    }
}