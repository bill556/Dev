package com.sino.frame.common.ui

import android.util.Log
import android.view.View
import androidx.viewbinding.ViewBinding
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sino.frame.base.ktx.observeLiveData
import com.sino.frame.base.mvvm.v.BaseFrameActivity
import com.sino.frame.base.mvvm.vm.BaseViewModel
import com.sino.frame.base.utils.ActivityStackManager
import com.sino.frame.base.utils.AndroidBugFixUtils
import com.sino.frame.base.utils.StateLayoutEnum
import com.sino.frame.common.loadsir.EmptyCallback
import com.sino.frame.common.loadsir.LoadingCallback

/**
 * Activity基类
 *
 * @author
 * @since 8/27/20
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>() {

    /**
     * loadSir
     */
    private var mLoadService: LoadService<*>? = null

    /**
     * 默认实现订阅加载过度页
     * @param view View 需要填充的view
     */
    fun setLoadSir(view: View) {
        if (mLoadService == null) {
            mLoadService = LoadSir.getDefault().register(
                view,
                Callback.OnReloadListener { onRetryBtnClick() } as Callback.OnReloadListener)
        }
        observeLiveData(mViewModel.stateViewLD, action = {
            when (it) {
                StateLayoutEnum.HIDE -> {
                    mLoadService?.showSuccess()
                }
                StateLayoutEnum.LOADING -> {
                    mLoadService?.showCallback(LoadingCallback::class.java)
                }
                StateLayoutEnum.NO_DATA -> {
                    mLoadService?.showCallback(EmptyCallback::class.java)
                }
                StateLayoutEnum.ERROR -> {
                }
            }
        })
    }

    /**
     * 失败重试,重新加载事件
     */
    protected abstract fun onRetryBtnClick()

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "ActivityStack: ${ActivityStackManager.activityStack}")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
    }
}