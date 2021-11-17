package com.sino.frame.home.ui.repo

import androidx.lifecycle.MutableLiveData
import com.sino.frame.base.mvvm.m.BaseRepository
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.common.helper.responseCodeExceptionHandler
import com.sino.frame.home.net.HomeApiService
import javax.inject.Inject

/**
 * HomeFragment的数据仓库层
 *
 * @author
 * @since 2021/8/5 10:59 下午
 */
class HealthBloodFragmentRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: HomeApiService

    /**
     * 文章列表数据加载 first:是否是加载更多、second:加载更多是否成功
     */
    val articleLoadLD = MutableLiveData<Pair<Boolean, Boolean>>()


    /**
     * 获取tab数据
     * @return
     */
    suspend fun getTab() = request<List<AppletsMenuBean>> {
        mApi.getTab().run {
            responseCodeExceptionHandler(code, msg, successBlock = {
                emit(data)
            })
        }
    }
}