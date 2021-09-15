package com.sino.frame.base.utils.status.imp

import android.os.Bundle
import com.sino.frame.base.utils.status.ViewStatusHelper

/**
 *
 * @Author:         Bill
 * @CreateDate:     2021/8/18 9:23 上午
 * @Description:    视图，activity，fragment重建帮助类
 *
 */
open class BaseFrameViewStatusHelperImp(parentViewStatusHelper: ViewStatusHelper? = null) : ViewStatusHelper(parentViewStatusHelper) {
    /**
     * 重建标记key 以包名保存数据可以防止嵌套层级出现重复Key
     */
    private val KEY_RECREATE =
        "com.quyunshuo.androidbaseframemvvm.base.utils.status.BaseFrameViewStatusHelperImp.Recreate"

    /**
     * 是否重建
     */
    var isRecreate = false
        private set


    /**
     * 恢复状态
     */
    override fun onRestoreInstanceStatus(savedInstanceState: Bundle?) {
        super.onRestoreInstanceStatus(savedInstanceState)
        isRecreate = savedInstanceState?.getBoolean(KEY_RECREATE) ?: false
    }

    /**
     * 保存状态
     */
    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        bundle.putBoolean(KEY_RECREATE, true)
    }
}