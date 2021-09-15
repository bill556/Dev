package com.sino.frame.base.mvvm.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.sino.frame.base.mvvm.vm.BaseViewModel
import com.sino.frame.base.utils.BindingReflex
import com.sino.frame.base.utils.EventBusRegister
import com.sino.frame.base.utils.EventBusUtils
import com.sino.frame.base.utils.status.ViewStatusHelper
import com.sino.frame.base.utils.status.imp.BaseFrameViewStatusHelperImp

/**
 * Fragment基类
 *
 * @author
 * @since 8/27/20
 */
abstract class BaseFrameFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameStatusFragment(), FrameView<VB> {

    /**
     * 私有的 ViewBinding 此写法来自 Google Android 官方
     */
    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    /**
     * viewModel
     */
    protected abstract val mViewModel: VM

    /**
     * 基础UI状态管理工具
     */
    private lateinit var mBaseStatusHelper: BaseFrameViewStatusHelperImp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)
        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)

        setStatusBar()
        _binding?.initView()
        _binding?.initEvent()
        initObserve()
        initRequestData()
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    open fun setStatusBar() {}

    /**
     * 是否是重建
     * @return Boolean
     */
    override fun isRecreate(): Boolean = mBaseStatusHelper.isRecreate

    /**
     * 视图重建辅助
     * @return ViewStatusHelper
     */
    override fun onRegisterStatusHelper(): ViewStatusHelper? {
        mBaseStatusHelper = BaseFrameViewStatusHelperImp(super.onRegisterStatusHelper())
        return mBaseStatusHelper
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
    }
}