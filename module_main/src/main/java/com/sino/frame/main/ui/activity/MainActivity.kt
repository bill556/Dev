package com.sino.frame.main.ui.activity

import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sino.frame.base.utils.BarUtils
import com.sino.frame.common.constant.RouteUrl
import com.sino.frame.common.ui.BaseActivity
import com.sino.frame.main.R
import com.sino.frame.main.adapter.ViewPagerAdapter
import com.sino.frame.main.databinding.MainActivityMainBinding
import com.sino.frame.main.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页
 *
 * @author
 * @since 2021/7/28 9:50 下午
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Main.MainActivity)
class MainActivity : BaseActivity<MainActivityMainBinding, MainViewModel>() {

    /**
     * MainActivity的ViewModel 通过Hilt自动注入
     */
    override val mViewModel: MainViewModel by viewModels()

    /**
     * ViewPager适配器
     */
    private val mVPAdapter by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewPagerAdapter(this, mViewModel.fragments)
    }

    companion object {
        const val INDEX_HOME = 0
        const val INDEX_SERVICE = 1
        const val INDEX_FIND = 2
        const val INDEX_ME = 3
    }

    override fun setStatusBar() {
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
    }

    override fun MainActivityMainBinding.initView() {
        // 底部导航栏设置
        vBottomNavigationView.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.vNavHome -> vViewPager.currentItem = INDEX_HOME
                    R.id.vNavService -> vViewPager.currentItem = INDEX_SERVICE
                    R.id.vNavFind -> vViewPager.currentItem = INDEX_FIND
                    R.id.vNavMe -> vViewPager.currentItem = INDEX_ME
                }
                true
            }
        }
        // ViewPager2设置
        vViewPager.run {
            adapter = mVPAdapter
            offscreenPageLimit = 3
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val menu = vBottomNavigationView.menu
                    when (position) {
                        INDEX_HOME -> menu.getItem(INDEX_HOME).isChecked = true
                        INDEX_SERVICE -> menu.getItem(INDEX_SERVICE).isChecked = true
                        INDEX_FIND -> menu.getItem(INDEX_FIND).isChecked = true
                        INDEX_ME -> menu.getItem(INDEX_ME).isChecked = true
                    }
                }
            })
        }
    }

    override fun MainActivityMainBinding.initEvent() {
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    override fun onRetryBtnClick() {

    }
}