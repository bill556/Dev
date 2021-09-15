package com.sino.frame.home.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.sino.frame.base.ktx.observeLiveData
import com.sino.frame.base.utils.BarUtils
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.ui.BaseFragment
import com.sino.frame.common.ui.helper.TabStyleHelper
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.home.databinding.HomeFragmentHomeBinding
import com.sino.frame.home.ui.vm.HomeFragmentVM
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页Fragment
 *
 * @author
 * @since 2021/8/1 11:46 下午
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Home.HomeFragment)
class HomeFragment : BaseFragment<HomeFragmentHomeBinding, HomeFragmentVM>() {

    override val mViewModel: HomeFragmentVM by viewModels()

    override fun setStatusBar() {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.tvTitle)
    }

    private lateinit var tabLayoutMediator: TabLayoutMediator
    override fun HomeFragmentHomeBinding.initView() {
        setLoadSir(container)
        tabLayoutMediator =
            TabLayoutMediator(tabLayout, vpHomeContent) { _: TabLayout.Tab?, _: Int -> }
    }

    override fun HomeFragmentHomeBinding.initEvent() {
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vpHomeContent.currentItem = tab.position
                TabStyleHelper.updateText(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                TabStyleHelper.updateText(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun initObserve() {
        observeLiveData(mViewModel.getTabData(), ::processTab)
    }

    override fun initRequestData() {
        mViewModel.requestTabList()
    }

    private lateinit var pageAdapter: FragmentStateAdapter

    private fun processTab(categoryList: List<AppletsMenuBean>?) {
        val categoryShowList: MutableList<AppletsMenuBean> = ArrayList()
        if (categoryList != null) {
            for (appletsMenuBean in categoryList) {
                if (appletsMenuBean.open == 1) {
                    categoryShowList.add(appletsMenuBean)
                }
            }
        }
        pageAdapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return categoryShowList.size
            }

            override fun createFragment(position: Int): Fragment {
                if (categoryShowList.size > 0) {
                    when (categoryShowList[position].menuId) {
                        "10001" -> return HealthBloodFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10002" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10003" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10004" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10005" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10006" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                        "10007" -> return EmptyFragment.newInstance(
                            categoryShowList[position].menuId.toInt(),
                            categoryShowList[position].name
                        )
                    }
                }
                return EmptyFragment.newInstance(
                    categoryList!![position].menuId.toInt(),
                    categoryList[position].name
                )
            }
        }
        mBinding.vpHomeContent.offscreenPageLimit = 2
        mBinding.vpHomeContent.adapter = pageAdapter
        tabLayoutMediator.detach()
        tabLayoutMediator.attach()
        mBinding.tabLayout.removeAllTabs()
        for (appletsMenuBean in categoryShowList) {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(appletsMenuBean.name))
        }
        mBinding.tabLayout.selectTab(mBinding.tabLayout.getTabAt(0))
    }

    override fun onRetryBtnClick() {
    }
}