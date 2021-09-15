package com.sino.frame.service

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.sino.frame.base.mvvm.vm.EmptyViewModel
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.ui.BaseFragment
import com.sino.frame.service.databinding.ServiceFragmentServiceBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 个人中心 和 更多 Fragment
 *
 * @author
 * @since 2021/8/1 11:44 下午
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Service.ServiceFragment)
class ServiceFragment : BaseFragment<ServiceFragmentServiceBinding, EmptyViewModel>() {

    override val mViewModel: EmptyViewModel by viewModels()

    override fun ServiceFragmentServiceBinding.initView() {
    }

    override fun ServiceFragmentServiceBinding.initEvent() {
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    override fun onRetryBtnClick() {

    }
}