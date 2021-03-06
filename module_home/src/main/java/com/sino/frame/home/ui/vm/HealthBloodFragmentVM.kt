package com.sino.frame.home.ui.vm

import androidx.lifecycle.MutableLiveData
import com.sino.frame.base.ktx.launchIO
import com.sino.frame.base.mvvm.vm.BaseViewModel
import com.sino.frame.base.utils.toast
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.home.ui.repo.HomeFragmentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * HomeFragment 的 VM 层
 *
 * @author
 * @since 2021/8/5 11:11 下午
 */
@HiltViewModel
class HealthBloodFragmentVM @Inject constructor(private val mRepo: HomeFragmentRepo) :
    BaseViewModel() {

    /**
     * tab数据
     */
    val tabData = MutableLiveData<List<AppletsMenuBean>>()
    fun getTabData() {
        launchIO {
            mRepo.getTabList()
                .catch { toast(it.message ?: "") }
                .collect {
                    tabData.postValue(it)
                }
        }
    }
}