package com.sino.frame.home.ui.vm

import androidx.lifecycle.liveData
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
class HomeFragmentVM @Inject constructor(private val mRepo: HomeFragmentRepo) : BaseViewModel() {

    /**
     * 不保存数据库的数据请使用以下方法
     */
//    //  私有的 MutableLiveData 可变的，对内访问
//    private val _tabData = MutableLiveData<List<AppletsMenuBean>>()
//
//    //  对外暴露不可变的 LiveData，只能查询
//    val tabData: LiveData<List<AppletsMenuBean>> = _tabData
//
//    fun getTabData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            mRepo.getTabList()
//                .catch { toast(it.message ?: "") }
//                .collect {
//                    _tabData.postValue(it)
//                }
//        }
//    }
    /**
     * 存储当前展示的TabList
     */
    private var showTabList: List<AppletsMenuBean>? = null

    /**
     * 发起网络请求，刷到的数据会直接同步到数据库
     */
    fun requestTabList() {
        changeStateView(loading = true)
        launchIO {
            mRepo.requestTabList()
            changeStateView(hide = true)
        }
    }

    /**
     * 监听数据库数据改变，一旦数据库发生改变，直接更新到界面上
     * 这里首先拉取一次数据库数据，然后会获取网络数据
     * 性能优化：如果获取到的数据 menuId及open是一样的，可以不重新emit
     * 如果两个数量不相等，则更新
     */
    fun getTabData() = liveData {
        mRepo.getTabList()
            .catch { toast(it.message ?: "") }
            .collect {
                if (!showTabList.isNullOrEmpty()) {
                    // 否则判断是否有展开变更
                    if (compareListNotSame(it)) {
                        showTabList = it
                        emit(it)
                    }
                } else {
                    showTabList = it
                    emit(it)
                }
            }
    }

    /**
     * 对比TabList是否相等
     *
     *  @return  true  不相等 false 相等
     */
    private fun compareListNotSame(it: List<AppletsMenuBean>): Boolean {
        var isNotSame = false
        for (newTab in it) {
            for (oldTab in showTabList!!) {
                // 如果Id相等，但是open状态不相等，将isNotSame置true并退出循环
                if (isNotSame(newTab, oldTab)) {
                    isNotSame = true
                    break
                }
            }
            if (isNotSame) break
        }
        return isNotSame
    }

    /**
     * 对比单个Tab对象是否相等
     */
    private fun isNotSame(
        newTab: AppletsMenuBean,
        oldTab: AppletsMenuBean
    ) = newTab.menuId == oldTab.menuId && (newTab.open != oldTab.open || newTab.sort != oldTab.sort)
}