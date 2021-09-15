package com.sino.frame.home.ui.fragment

import com.sino.frame.common.ui.BaseFragment
import com.sino.frame.base.mvvm.vm.BaseViewModel
import com.sino.frame.home.ui.fragment.EmptyFragment
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.sino.frame.home.databinding.HomeFragmentEmptyBinding
import com.sino.frame.home.databinding.HomeFragmentHealthBloodBinding
import com.sino.frame.home.databinding.HomeFragmentHomeBinding
import com.sino.frame.home.ui.vm.HomeFragmentVM
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Bill
 * @CreateDate: 2021年03月22日10:38:29
 * @Description: 健康-血糖
 */
@AndroidEntryPoint
class HealthBloodFragment : BaseFragment<HomeFragmentHealthBloodBinding, BaseViewModel>() {

    companion object {
        private const val CATEGORY_ID = "category_id"
        private const val CATEGORY_NAME = "category_name"
        fun newInstance(categoryID: Int, categoryName: String?): HealthBloodFragment {
            val bundle = Bundle()
            bundle.putInt(CATEGORY_ID, categoryID)
            bundle.putString(CATEGORY_NAME, categoryName)
            val fragment = HealthBloodFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val mViewModel: BaseViewModel by viewModels()

    override fun HomeFragmentHealthBloodBinding.initView() {
    }

    override fun HomeFragmentHealthBloodBinding.initEvent() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    override fun onRetryBtnClick() {

    }
}