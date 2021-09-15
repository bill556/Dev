package com.sino.frame.me

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.orhanobut.logger.Logger
import com.sino.frame.base.mvvm.vm.EmptyViewModel
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.mmkv.MMKVFileDemo
import com.sino.frame.common.mmkv.UserInfo
import com.sino.frame.common.ui.BaseFragment
import com.sino.frame.me.databinding.MeFragmentMeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 个人中心 和 更多 Fragment
 *
 * @author
 * @since 2021/8/1 11:44 下午
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Me.MeFragment)
class MeFragment : BaseFragment<MeFragmentMeBinding, EmptyViewModel>() {

    override val mViewModel: EmptyViewModel by viewModels()

    override fun MeFragmentMeBinding.initView() {
    }

    override fun MeFragmentMeBinding.initEvent() {
        tv.setOnClickListener {
            var user = UserInfo()
            user.sex = 99
            user.accountId = "accountid"
            MMKVFileDemo.userinfo = user
        }
        tv2.setOnClickListener {
            Logger.e(MMKVFileDemo.userinfo.toString())
        }
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    override fun onRetryBtnClick() {

    }
}

