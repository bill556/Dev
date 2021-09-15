package com.sino.frame.common.ui

import android.view.View
import androidx.viewbinding.ViewBinding
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sino.frame.base.ktx.observeLiveData
import com.sino.frame.base.mvvm.v.BaseFrameFragment
import com.sino.frame.base.mvvm.vm.BaseViewModel
import com.sino.frame.base.utils.StateLayoutEnum
import com.sino.frame.common.loadsir.EmptyCallback
import com.sino.frame.common.loadsir.LoadingCallback

/**
 * Fragment基类
 *
 * @author
 * @since 8/27/20
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameFragment<VB, VM>() {

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
}
