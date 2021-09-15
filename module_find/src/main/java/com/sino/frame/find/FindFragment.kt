package com.sino.frame.find

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.sino.frame.base.mvvm.vm.EmptyViewModel
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.ui.BaseFragment
import com.sino.frame.find.databinding.FindFragmentFindBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 发现首页Fragment
 *
 * @author
 * @since 2021/8/1 11:44 下午
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Find.FindFragment)
class FindFragment : BaseFragment<FindFragmentFindBinding, EmptyViewModel>() {

    override val mViewModel: EmptyViewModel by viewModels()

    override fun FindFragmentFindBinding.initView() {
    }

    override fun FindFragmentFindBinding.initEvent() {
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    override fun onRetryBtnClick() {
    }
}